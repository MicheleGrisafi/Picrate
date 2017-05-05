package androidlab.fotografando;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
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
import android.support.v4.content.ContextCompat;
import android.util.Size;
import android.util.SparseIntArray;
import android.util.TypedValue;
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

public class camera extends Activity {

    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT = 1;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAIT_LOCK = 1;
    private int mCaptureState;


    private RelativeLayout topOverlay;
    private RelativeLayout bottomOverlay;
    private int topLayerHeight = 0,bottomLayerHeight = 0;

    private Intent checkPhotoIntent;
    private Button mTakePhotoButton;
    private TextureView mTextureView;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //Toast.makeText(getApplicationContext(),"TeztureView is avaiable",Toast.LENGTH_LONG).show();
            setupCamera(width,height);
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

    private CameraDevice mCameraDevice;
    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            Toast.makeText(camera.this, "camera opened", Toast.LENGTH_SHORT).show();
            startPreview();
        }

        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            super.onClosed(camera);
            Toast.makeText(camera.this, "Camera closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera,  int error) {
            camera.close();
            switch (error){
                case CameraDevice.StateCallback.ERROR_CAMERA_DEVICE:
                    Toast.makeText(camera.this, "ERROR_CAMERA_DEVICE", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_CAMERA_DISABLED:
                    Toast.makeText(camera.this, "ERROR_CAMERA_DISABLED", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_CAMERA_IN_USE:
                    Toast.makeText(camera.this, "ERROR_CAMERA_IN_USE", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_CAMERA_SERVICE:
                    Toast.makeText(camera.this, "ERROR_CAMERA_SERVICE", Toast.LENGTH_SHORT).show();
                    break;
                case CameraDevice.StateCallback.ERROR_MAX_CAMERAS_IN_USE:
                    Toast.makeText(camera.this, "ERROR_MAX_CAMERAS_IN_USE", Toast.LENGTH_SHORT).show();
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
    private final ImageReader.OnImageAvailableListener mOnImageAvaiableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            mBackgroundHandler.post(new ImageSaver(reader.acquireLatestImage()));
        }
    };
    private class ImageSaver implements Runnable{
        private final Image mImage;
        private ImageSaver(Image image){
            mImage = image;
        }
        @Override
        public void run() {
            ByteBuffer byteBuffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);

            //inizio
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

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


            //Bitmap cropped = Bitmap.createBitmap(bitmap, 0, 0, width, width, matrix, true);
            bitmap.recycle();

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(mPhotoFileName);
                cropped.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            checkPhotoIntent.putExtra("fileName",mPhotoFileName);
            startActivityForResult(checkPhotoIntent,REQUEST_CODE);
            //fine
            /*
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(mPhotoFileName);
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                mImage.close();
                if(fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }
    }



    private CaptureRequest mPreviewCaptureRequest;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraCaptureSession mPreviewCaptureSession;
    private CameraCaptureSession.CaptureCallback mPreviewCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        private void process(CaptureResult captureResult){
            switch (mCaptureState){
                case STATE_PREVIEW:
                    break;
                case STATE_WAIT_LOCK:
                    //Toast.makeText(camera.this, "SATE WAIT LOCK", Toast.LENGTH_SHORT).show();
                   // mCaptureState = STATE_PREVIEW;
                    Integer afState = captureResult.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == CaptureRequest.CONTROL_AF_STATE_FOCUSED_LOCKED /*|| afState == CaptureRequest.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED*/){
                        unLockFocus();
                        Toast.makeText(getApplicationContext(),"AF locked!",Toast.LENGTH_LONG).show();
                        startStillCaptureRequest();
                    }else{
                        //Toast.makeText(camera.this, "af state WRONG", Toast.LENGTH_SHORT).show();
                        //lockFocus(true);
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
            Toast.makeText(getApplicationContext(),"Focus Lock Unsuccessful",Toast.LENGTH_LONG).show();
        }
    };
    private File mPhotoFolder;
    private String mPhotoFileName;
    private File mPhotoFile;
    private static SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0,0);
        ORIENTATIONS.append(Surface.ROTATION_90,90);
        ORIENTATIONS.append(Surface.ROTATION_180,180);
        ORIENTATIONS.append(Surface.ROTATION_270,270);
    }
    private static class CompareSizeByArea implements Comparator<Size>{
        @Override
        public int compare(Size lhs, Size rhs){
            return Long.signum((long)lhs.getWidth() * lhs.getHeight() / (long)rhs.getWidth() * rhs.getHeight());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        createPhotoFolder();
        checkPhotoIntent = new Intent(this,checkPhoto.class);
        mTextureView = (TextureView)findViewById(R.id.textureView2);
        mTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*lockFocus(false);
                unLockFocus();*/
            }
        });

        mTakePhotoButton = (Button)findViewById(R.id.btnShoot);
        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shootPhoto();
            }
        });
        checkWriteStoragePermission();
        topOverlay = (RelativeLayout)findViewById(R.id.topLayer);
        bottomOverlay = (RelativeLayout)findViewById(R.id.bottomLayer);

    }

    @Override
    protected void onResume(){
        super.onResume();

        startBackgroundThread();

        if(mTextureView.isAvailable()){
            setupCamera(mTextureView.getWidth(),mTextureView.getHeight());
            connectCamera();
        }else{
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    @Override
    protected void onPause(){
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

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
        // Get the preview size
        int previewWidth = mTextureView.getMeasuredWidth();
        int previewHeight = mTextureView.getMeasuredHeight();
        int btnDimension = mTakePhotoButton.getMeasuredHeight();
        // Set the height of the overlay so that it makes the preview a square




        if (previewHeight-previewWidth >= btnDimension) {
            if ((previewHeight - previewWidth) / 2 >= btnDimension) {
                topLayerHeight = (previewHeight - previewWidth) / 2;
                bottomLayerHeight = (previewHeight - previewWidth) / 2;
            } else {
                bottomLayerHeight = previewHeight - previewWidth;
                topLayerHeight = 0;
            }
        }else{
            /*
            btnDimension = previewHeight - previewWidth;
            mTakePhotoButton.getLayoutParams().height = btnDimension;
            mTakePhotoButton.getLayoutParams().width = btnDimension;*/
        }


        RelativeLayout.LayoutParams overlayTopParams = (RelativeLayout.LayoutParams) topOverlay.getLayoutParams();
        overlayTopParams.height = topLayerHeight;

        RelativeLayout.LayoutParams overlayBottomParams = (RelativeLayout.LayoutParams) bottomOverlay.getLayoutParams();
        overlayBottomParams.height = bottomLayerHeight;

        topOverlay.setLayoutParams(overlayTopParams);
        bottomOverlay.setLayoutParams(overlayBottomParams);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CAMERA_PERMISSION_RESULT:
                try {
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getApplicationContext(), R.string.PermissionNotGranted, Toast.LENGTH_SHORT).show();
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    try {
                        createPhotoFileName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(this,R.string.permissionStorageDenied,Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                File image = new File(mPhotoFileName);
                boolean deleted = image.delete();

                if (deleted) {
                    Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
                    mPhotoFileName = null;
                }
                else
                    Toast.makeText(this, "CANNOT DELETE! " + mPhotoFileName, Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this, "Photo Created", Toast.LENGTH_SHORT).show();
                break;
        }
    }


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
                int deviceOrientation = getWindowManager().getDefaultDisplay().getRotation();
                mTotalrotation = sensorToDeviceRotation(cameraCharacteristics,deviceOrientation);
                boolean swapRotation = mTotalrotation == 90 || mTotalrotation == 270;
                int rotatedWidth = width;
                int rotatedHeight = height;
                if (swapRotation){
                    rotatedWidth = height;
                    rotatedHeight = width;
                }
                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),rotatedWidth,rotatedHeight);
                mImageSize = chooseOptimalSize(map.getOutputSizes(ImageFormat.JPEG),rotatedWidth,rotatedHeight);
                mImageReader = ImageReader.newInstance(mImageSize.getWidth(),mImageSize.getHeight(),ImageFormat.JPEG,1);
                mImageReader.setOnImageAvailableListener(mOnImageAvaiableListener,mBackgroundHandler);

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
                        Toast.makeText(this, R.string.permissionMessage, Toast.LENGTH_SHORT).show();
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
                    if (mCameraDevice == null){
                        return;
                    }
                    mPreviewCaptureRequest = mCaptureRequestBuilder.build();
                    mPreviewCaptureSession = session;
                    try {
                        //The thread could also be null!
                        mPreviewCaptureSession.setRepeatingRequest(mPreviewCaptureRequest,mPreviewCaptureCallback,mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(getApplicationContext(),R.string.GenericError,Toast.LENGTH_LONG).show();
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
            mCaptureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION,mTotalrotation);
            CameraCaptureSession.CaptureCallback stillCaptureCallback = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                    super.onCaptureStarted(session, request, timestamp, frameNumber);
                    try {
                        createPhotoFileName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    //unLockFocus();
                }

                @Override
                public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                    super.onCaptureFailed(session, request, failure);
                    Toast.makeText(camera.this, "Captured failed", Toast.LENGTH_SHORT).show();
                }
            };
            mPreviewCaptureSession.capture(mCaptureRequestBuilder.build(),stillCaptureCallback,null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
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
        int sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
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
    private void createPhotoFolder(){
        File photoFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mPhotoFolder = new File(photoFile,"Fotografando");
        if(!mPhotoFolder.exists()){
            mPhotoFolder.mkdirs();
        }
    }
    private File createPhotoFileName() throws IOException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prepend = "PHOTO_" + timestamp + "_";
        File photoFile = File.createTempFile(prepend,".jpg",mPhotoFolder);
        mPhotoFileName = photoFile.getAbsolutePath();
        mPhotoFile = photoFile;
        return photoFile;
    }
    private void checkWriteStoragePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                /*try {
                    createPhotoFileName();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }else{
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this,R.string.askWriteToSDpermission,Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_RESULT);
            }
        }else{
           /* try {
                createPhotoFileName();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
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
        try {
            mPreviewCaptureSession.capture(mCaptureRequestBuilder.build(),mPreviewCaptureCallback,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    private void shootPhoto() {
        lockFocus(true);
    }

}
