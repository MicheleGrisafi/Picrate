package picrate.app.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import picrate.app.R;
import picrate.app.assets.objects.BitmapHelper;
import picrate.app.assets.objects.MyApp;

/**
 * Creata da Michele Grisafi
 * Crediti a "Mobile Application Tutorials", canale youtube
 *
 * Activity per la gestione della fotocamera.
 * I Javadoc di questa activity non sono riportati in quanto le funzionalità sono complesse e difficili da documentare in maniere soddisfacente
 */
public class ActivityCamera extends Activity {
    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = 1;
    private static final int REQUEST_CAMERA = 5;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAIT_LOCK = 1;
    private static final int STATE_PICTURE_CAPTURED = 2;
    private int mCaptureState;
    private boolean shooting = false;

    private Intent inIntent;
    private Intent outIntent;
    private Intent checkPhotoIntent;


    private RelativeLayout topOverlay;
    private RelativeLayout bottomOverlay;
    private int topLayerHeight = 0,bottomLayerHeight = 0;

    private Button mTakePhotoButton;
    private TextureView mTextureView;

    /** SurfaceTexture listener **/
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //Inizalizzo fotocamera appena la SurfaceTexture... è disponibile
            setupCamera(width,height);
            transformImage(width,height);
            connectCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private void transformImage(int width, int height){
        if(mPreviewSize != null && mTextureView != null){
            Matrix matrix = new Matrix();
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            RectF textureRectF = new RectF(0,0,width,height);
            RectF previewRectF = new RectF(0,0,mPreviewSize.getHeight(),mPreviewSize.getWidth());
            float centerX = textureRectF.centerX();
            float centerY = textureRectF.centerY();
            if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
                previewRectF.offset(centerX-previewRectF.centerX(),centerY-previewRectF.centerY());
                matrix.setRectToRect(textureRectF,previewRectF,Matrix.ScaleToFit.FILL);
                float scale = Math.max((float)width/mPreviewSize.getWidth(),(float)height / mPreviewSize.getHeight());
                matrix.postScale(scale,scale,centerX,centerY);
                matrix.postRotate(90 *(rotation-2),centerX,centerY);

            }
            mTextureView.setTransform(matrix);
        }
    }

    /** Camera device **/
    private CameraDevice mCameraDevice;
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            //Camera aperta -> assegno la camera effettiva e inizio la ripresa
            mCameraDevice = camera;
            startPreview();
        }

        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            super.onClosed(camera);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            //Chiudo la fotocamera
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera,  int error) {
            // Gestisco i vari errori
            camera.close();
            switch (error){
                case CameraDevice.StateCallback.ERROR_CAMERA_DEVICE:
                    Toast.makeText(ActivityCamera.this, "ERROR_CAMERA_DEVICE", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_CAMERA_DISABLED:
                    Toast.makeText(ActivityCamera.this, "ERROR_CAMERA_DISABLED", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_CAMERA_IN_USE:
                    Toast.makeText(ActivityCamera.this, "ERROR_CAMERA_IN_USE", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_CAMERA_SERVICE:
                    Toast.makeText(ActivityCamera.this, "ERROR_CAMERA_SERVICE", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_MAX_CAMERAS_IN_USE:
                    Toast.makeText(ActivityCamera.this, "ERROR_MAX_CAMERAS_IN_USE", Toast.LENGTH_SHORT).show();
                    break;
            }
            mCameraDevice = null;
        }
    };

    private HandlerThread mBackgroundHandlerThread;
    private Handler mBackgroundHandler;
    private String mCameraId;
    private Size mPreviewSize;
    private int mTotalrotation;
    private Size mImageSize;
    private ImageReader mImageReader;
    /** Acquisizione immagine **/
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage()));//it was latetest image
        }
    };

    /** Salvataggio immagine **/
    private class ImageSaver implements Runnable{
        private final Image mImage;
        private ImageSaver(Image image){
            mImage = image;
        }
        @Override
        public void run() {
            //Decodifico l'immagine per trasformarla in bitmap
            ByteBuffer byteBuffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);


            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length,options);



            //Ritaglio l'immagine secondo il quadrato
            int destHeight;
            Matrix matrix = new Matrix();
            Bitmap cropped;
            if (bitmap.getWidth() >= bitmap.getHeight()){
                matrix.postRotate(90);
                destHeight = bitmap.getWidth() - (bitmap.getWidth() * topLayerHeight) / mTextureView.getHeight();
                cropped = Bitmap.createBitmap(
                        bitmap,
                        bitmap.getWidth() - destHeight,
                        0,
                        bitmap.getHeight(),
                        bitmap.getHeight(),matrix,true
                );
            }else{
                matrix.postRotate(0);
                destHeight = bitmap.getHeight() - (bitmap.getHeight() * topLayerHeight) / mTextureView.getHeight();
                cropped = Bitmap.createBitmap(
                        bitmap,
                        0,
                        bitmap.getHeight() - destHeight,
                        bitmap.getWidth(),
                        bitmap.getWidth(),matrix,true
                );
            }

            //Scrivo l'immagine su un file JPEG
            //TODO: BUG PNG: alcuni telefoni salvano in png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(mPhotoFileName);
                cropped.compress(Bitmap.CompressFormat.JPEG, 50, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                try {
                    if (out != null) {
                        out.close();
                        out = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Reciclo l'immagine ritagliata
            bitmap.recycle();
            cropped.recycle();

            bitmap = null;
            cropped = null;
            //Inserisco l'immagine nell'intent da passare al controllo fotografico

            checkPhotoIntent.putExtra("fileName",mPhotoFileName);
            checkPhotoIntent.putExtra("price",inIntent.getIntExtra("price",0));
            startActivityForResult(checkPhotoIntent,REQUEST_CODE);
            //finish();
        }
    }

    /** Richieste di cattura della foto **/
    private CaptureRequest mPreviewCaptureRequest;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraCaptureSession mPreviewCaptureSession;
    private CameraCaptureSession.CaptureCallback mPreviewCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        private void process(CaptureResult captureResult){
            switch (mCaptureState){
                case STATE_PREVIEW:
                    break;
                case STATE_WAIT_LOCK:
                    //Gestisco il fuoco
                    // mCaptureState = STATE_PREVIEW;
                    Integer afState = captureResult.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == CaptureRequest.CONTROL_AF_STATE_FOCUSED_LOCKED || afState == CaptureRequest.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED){
                        //unLockFocus();
                        //Toast.makeText(getApplicationContext(),"AF locked!",Toast.LENGTH_SHORT).show();
                        //Avvio cattura immagine
                        mCaptureState = STATE_PICTURE_CAPTURED;
                        startStillCaptureRequest();
                    }
                    break;
            }
        }
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            process(result);
        }
        @Override
        public void onCaptureFailed(CameraCaptureSession session, CaptureRequest request, CaptureFailure failure){
            super.onCaptureFailed(session,request,failure);
            Toast.makeText(MyApp.getAppContext(),"Focus Lock Unsuccessful",Toast.LENGTH_LONG).show();
        }
    };

    /** Gestione file **/
    private File mPhotoFolder;
    private String mPhotoFileName;
    private File mPhotoFile;
    private static SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }
    private static class CompareSizeByArea implements Comparator<Size>{
        @Override
        public int compare(Size lhs, Size rhs){
            return Long.signum((long)lhs.getWidth() * lhs.getHeight() / (long)rhs.getWidth() * rhs.getHeight());
        }
    }

    /** Gestione activity **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        createPhotoFolder();

        checkPhotoIntent = new Intent(this,ActivityCheckPhoto.class);
        inIntent = getIntent();
        outIntent = new Intent();

        mTextureView = (TextureView)findViewById(R.id.textureView2);
        mTakePhotoButton = (Button)findViewById(R.id.btnShoot);
        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shootPhoto();
            }
        });
        topOverlay = (RelativeLayout)findViewById(R.id.topLayer);
        bottomOverlay = (RelativeLayout)findViewById(R.id.bottomLayer);
        //checkWriteStoragePermission();
    }

    @Override
    protected void onResume(){
        super.onResume();
        startBackgroundThread();
        if(mTextureView.isAvailable()){
            setupCamera(mTextureView.getWidth(),mTextureView.getHeight());
            transformImage(mTextureView.getWidth(),mTextureView.getHeight());
            connectCamera();
        }else{
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
        checkWriteStoragePermission();
    }

    @Override
    protected void onPause(){
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if(!BitmapHelper.deleteFile(mPhotoFile))
            Toast.makeText(this, R.string.deletingLocalFileError, Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /** Rendo il layout fullscreen **/
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if(hasFocus){
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        resizeOverlay();
    }

    /** Gestisco i permessi **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA_PERMISSION_RESULT:
                try {
                    if( grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(MyApp.getAppContext(), R.string.camera_permission_denied, Toast.LENGTH_LONG).show();
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT:
                if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,R.string.storage_permission_denied,Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    /** Gestisco il risultato dell'activity controllo **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //riabilito la fotocamera
        shooting = false;
        switch (resultCode){
            case 0:
                //Immagine scartata
                if(!BitmapHelper.deleteFile(mPhotoFile))
                    Toast.makeText(this, R.string.deletingLocalFileError, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //Invio l'immagine all'activity principale per essere messa nell'imageview
                outIntent.putExtra("imageView",inIntent.getIntExtra("imageView",0));
                outIntent.putExtra("fileName",data.getStringExtra("fileName"));
                outIntent.putExtra("sessionID",inIntent.getIntExtra("sessionID",0));
                outIntent.putExtra("price",data.getIntExtra("price",0));
                outIntent.putExtra("lat",data.getDoubleExtra("lat",0));
                outIntent.putExtra("long",data.getDoubleExtra("long",0));
                setResult(1,outIntent);
                closeCamera();
                finish();
                break;
        }
    }

    /** Gestione della fotocamera **/
    private void closeCamera(){
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (mPreviewCaptureSession != null) {
                mPreviewCaptureSession.close();
                mPreviewCaptureSession = null;
            }
            if (mImageReader != null) {
                mImageReader.close();
                mImageReader = null;
            }

    }

    private void setupCamera(int width, int height){
        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
            for(String cameraId : cameraManager.getCameraIdList()){
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                /*
                int deviceOrientation = getWindowManager().getDefaultDisplay().getRotation();
                mTotalrotation = sensorToDeviceRotation(cameraCharacteristics,deviceOrientation);
                boolean swapRotation = mTotalrotation == 90 || mTotalrotation == 270;
                int rotatedWidth = width;
                int rotatedHeight = height;
                if (swapRotation){
                    rotatedWidth = height;
                    rotatedHeight = width;
                }*/
                Size largestImageSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new Comparator<Size>() {
                    @Override
                    public int compare(Size lhs, Size rhs) {
                        return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getHeight() * rhs.getWidth());
                    }
                });
                mImageReader = ImageReader.newInstance(largestImageSize.getWidth(),largestImageSize.getHeight(),ImageFormat.JPEG,1);
                mImageReader.setOnImageAvailableListener(mOnImageAvailableListener,mBackgroundHandler);
                mPreviewSize = getPreferredPreviewSize(map.getOutputSizes(SurfaceTexture.class),width,height);
               // mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),rotatedWidth,rotatedHeight);
                mImageSize = chooseOptimalSize(map.getOutputSizes(ImageFormat.JPEG),width,height);
                /*mImageReader = ImageReader.newInstance(mImageSize.getWidth(),mImageSize.getHeight(),ImageFormat.JPEG,1);
                mImageReader.setOnImageAvailableListener(mOnImageAvailableListener,mBackgroundHandler);*/
                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
    private void connectCamera(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    cameraManager.openCamera(mCameraId,mCameraDeviceStateCallback,mBackgroundHandler);
                }else{
                    if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                        Toast.makeText(this, R.string.ask_camera_permission, Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[] {android.Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION_RESULT);
                }
            }else{
                cameraManager.openCamera(mCameraId,mCameraDeviceStateCallback,mBackgroundHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void startPreview(){
        SurfaceTexture surfaceTexture = mTextureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(),mPreviewSize.getHeight());
        Surface previewSurface = new Surface(surfaceTexture);

        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface,mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (mCameraDevice != null){
                        mPreviewCaptureRequest = mCaptureRequestBuilder.build();
                        mPreviewCaptureSession = session;
                        try {
                            mPreviewCaptureSession.setRepeatingRequest(mPreviewCaptureRequest,mPreviewCaptureCallback,mBackgroundHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    //TODO: bug ad alcuni -> finisce in capturefailed
                    Toast.makeText(MyApp.getAppContext(),"error trying to configure capture session",Toast.LENGTH_LONG).show();
                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void startStillCaptureRequest(){
        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            mCaptureRequestBuilder.addTarget(mImageReader.getSurface());
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            mCaptureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));
            CameraCaptureSession.CaptureCallback stillCaptureCallback = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                    super.onCaptureStarted(session, request, timestamp, frameNumber);
                }

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    shooting = false;
                    unLockFocus();
                }

                @Override
                public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                    super.onCaptureFailed(session, request, failure);
                    Toast.makeText(ActivityCamera.this, "capture failed error", Toast.LENGTH_SHORT).show();
                    unLockFocus();
                }
            };
            mPreviewCaptureSession.stopRepeating();
            mPreviewCaptureSession.capture(mCaptureRequestBuilder.build(),stillCaptureCallback,null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /** Gestione Thread **/
    private void startBackgroundThread(){
        mBackgroundHandlerThread = new HandlerThread("Camera");
        mBackgroundHandlerThread.start();
        mBackgroundHandler = new Handler(mBackgroundHandlerThread.getLooper());
    }
    private void stopBackgroundThread(){
        mBackgroundHandlerThread.quitSafely();
        try {
            mBackgroundHandlerThread.join();
            mBackgroundHandlerThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static int sensorToDeviceRotation(CameraCharacteristics cameraCharacteristics, int deviceOrientation){
        int sensorOrientation = 0;
        if (cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) != null)
            sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        deviceOrientation = ORIENTATIONS.get(deviceOrientation);
        return (sensorOrientation + deviceOrientation + 360) % 360;
    }
    private static Size chooseOptimalSize(Size[] choices, int width,int height){
        List<Size> bigEnough = new ArrayList<Size>();
        for(Size option : choices){
            if(option.getHeight() == option.getWidth() * height/width && option.getWidth() >= width && option.getHeight() >= height){
                bigEnough.add(option);
            }
        }
        if(bigEnough.size() > 0){
            return Collections.min(bigEnough,new CompareSizeByArea());
        }else{
            return choices[0];
        }
    }
    private Size getPreferredPreviewSize(Size[] mapSizes, int width, int height){
        List<Size> collectorSizes = new ArrayList<>();
        for (Size option:mapSizes){
            if(width>height){
                if(option.getHeight() > height && option.getWidth()> width){
                    collectorSizes.add(option);
                }
            }else{
                if(option.getHeight() > width && option.getWidth()> height){
                    collectorSizes.add(option);
                }
            }
        }
        Size result = mapSizes[0];
        if(collectorSizes.size() > 0){
            result = Collections.min(collectorSizes, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }
            });
        }
        return result;
    }
    /** Gestione file e cartella **/
    private void createPhotoFolder(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                File photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                mPhotoFolder = new File(photoFile,getString(R.string.photo_folder_name));
                if(!mPhotoFolder.exists()){
                    if(!mPhotoFolder.mkdirs())
                        Toast.makeText(this, R.string.photo_folder_not_created, Toast.LENGTH_SHORT).show();
                }
            }else{
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,R.string.ask_storage_permission,Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
                //requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
            }
        }else{
            File photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            mPhotoFolder = new File(photoFile,getString(R.string.photo_folder_name));
            if(!mPhotoFolder.exists()){
                if(!mPhotoFolder.mkdirs())
                    Toast.makeText(this, R.string.photo_folder_not_created, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private File createPhotoFileName() throws IOException{
        //Creo foto con la data come nome
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prepend = "PHOTO_" + timestamp + "_";
        File photoFile = File.createTempFile(prepend,".jpg",mPhotoFolder);
        mPhotoFileName = photoFile.getAbsolutePath();
        mPhotoFile = photoFile;
        return photoFile;
    }

    /** Gestisco permessi di scrittura **/
    private void checkWriteStoragePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                try {
                    createPhotoFileName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,R.string.ask_storage_permission,Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
                //requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
            }
        }else{
           try {
                createPhotoFileName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** Metodi per la gestione del fuoco **/
    private void lockFocus(boolean shoot){
        try {
            mCaptureState = STATE_WAIT_LOCK;
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,CaptureRequest.CONTROL_AF_TRIGGER_START);
            mPreviewCaptureSession.capture(mCaptureRequestBuilder.build(),mPreviewCaptureCallback,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void unLockFocus(){
        mCaptureState = STATE_PREVIEW;
        mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,CaptureRequest.CONTROL_AF_TRIGGER_CANCEL);
        /*try {
            mPreviewCaptureSession.capture(mCaptureRequestBuilder.build(),mPreviewCaptureCallback,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }*/
    }
    private void shootPhoto() {
        //se già cliccato allora non faccio nulla
        //TODO bug persmission: cambiare posizione della permission
        if(!shooting) {
            shooting = true;
            lockFocus(true);
        }
    }

    /** Gestisco l'overlay per rendere la fotocamera quadrata **/
    private void resizeOverlay(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int previewWidth = displayMetrics.heightPixels;
        int previewHeight = displayMetrics.widthPixels;
        if (previewHeight < previewWidth){
            int tmp = previewHeight;
            previewHeight = previewWidth;
            previewWidth = tmp;
        }
        int btnDimension = mTakePhotoButton.getMeasuredHeight();

        // Modifico le dimensioni
        if (previewHeight-previewWidth >= btnDimension) {
            if ((previewHeight - previewWidth) / 2 >= btnDimension) {
                topLayerHeight = (previewHeight - previewWidth) / 2;
                bottomLayerHeight = (previewHeight - previewWidth) / 2;
            } else {
                bottomLayerHeight = previewHeight - previewWidth;
                topLayerHeight = 0;
            }
        }

        RelativeLayout.LayoutParams overlayTopParams = (RelativeLayout.LayoutParams) topOverlay.getLayoutParams();
        overlayTopParams.height = topLayerHeight;

        RelativeLayout.LayoutParams overlayBottomParams = (RelativeLayout.LayoutParams) bottomOverlay.getLayoutParams();
        overlayBottomParams.height = bottomLayerHeight;

        topOverlay.setLayoutParams(overlayTopParams);
        bottomOverlay.setLayoutParams(overlayBottomParams);

        topOverlay.bringToFront();
        bottomOverlay.bringToFront();
    }
}
