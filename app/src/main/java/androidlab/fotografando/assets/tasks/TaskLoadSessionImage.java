package androidlab.fotografando.assets.tasks;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidlab.DB.DAO.PhotoDAO;
import androidlab.DB.DAO.implementations.PhotoDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.activities.ActivityCamera;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.views.ImageViewChallenge;
import androidlab.fotografando.assets.listeners.OnClickListenerCamera;
import androidlab.fotografando.assets.listeners.RequestListenerGlideProgressBar;

/**
 * Created by miki4 on 29/05/2017.
 */

/** Carica le immagini utente nella lista delle challenge **/
public class TaskLoadSessionImage extends AsyncTask<Void,Void,ArrayList<Photo>> {

    private ChallengeSession session;
    private ArrayList<ImageViewChallenge> imageViews;
    private Context context;
    private FragmentActivity activity;
    private ProgressBar progressBar1,progressBar2;

    private Utente user;
    private PhotoDAO dao;
    private int requestCode;

    public TaskLoadSessionImage(Context context, ChallengeSession session, ArrayList<ImageViewChallenge> imageViews,
                                int requestCode, FragmentActivity activity, ProgressBar progressBar1, ProgressBar progressBar2){
        this.session = session;
        this.context = context;
        this.imageViews = imageViews;
        this.requestCode = requestCode;
        user = AppInfo.getUtente();
        this.activity = activity;
        this.progressBar1 = progressBar1;
        this.progressBar2 = progressBar2;
        progressBar2.setVisibility(ProgressBar.VISIBLE);
        progressBar1.setVisibility(ProgressBar.VISIBLE);
    }
    @Override
    protected void onPreExecute() {
        dao = new PhotoDAO_DB_impl();
        dao.open();
    }
    @Override
    protected ArrayList<Photo> doInBackground(Void... params) {
        ArrayList<Photo> pictures;
        pictures = dao.getPhotosSession(user,session);
        return pictures;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> pictures) {
        // TODO: provare a scaricare l'immagine bitmap in formato grande per implementare lo zoom
        Photo foto;
        ImageViewChallenge imageView;
        final Intent intent = new Intent(context,ActivityCamera.class);

        //Carica le foto nelle imageView
        if(pictures != null) {
            for (int i = 0; i < pictures.size(); i++){
                foto = pictures.get(i);
                imageView = imageViews.get(i);
                if (foto != null) {
                    RequestListenerGlideProgressBar requestListener;
                    if (i == 0) {
                        requestListener = new RequestListenerGlideProgressBar(progressBar1);
                    }
                    else {
                        requestListener = new RequestListenerGlideProgressBar(progressBar2);
                    }
                    Glide.with(context)
                            .load(foto.getImage())
                            .listener(requestListener)
                            .into(imageView);
                    imageView.setPhoto(foto);
                }
            }
        }

        //Controlla quali imageView è possibile cliccare
        ArrayList<Boolean> tags = new ArrayList<>(imageViews.size());
        for (int i = 0; i<imageViews.size();i++){
            imageView = imageViews.get(i);
            if(pictures != null && !pictures.isEmpty()){
                //immagine utente
                //TODO: inserire zoom nel listener
                imageView.setOnClickListener(null);
                pictures.remove(0);
                tags.add(true);
            }else{
                //immagine vuota
                tags.add(false);
                if(i == 0)
                    progressBar1.setVisibility(ProgressBar.GONE);
                if(i == 1)
                    progressBar2.setVisibility(ProgressBar.GONE);
                if ( i> 0 && !tags.get(i-1)){
                    imageView.setImageResource(R.drawable.ic_add_a_photo_gray_24dp);
                    imageView.setOnClickListener(null);
                }else{
                    imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
                    intent.putExtra("imageView",i);
                    intent.putExtra("sessionID",session.getIDSession());

                    //imposto la dialog per il pagamento della seconda foto
                    if(i == 1) {
                        intent.putExtra("secondPhoto",true);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = new Dialog(activity);
                                dialog.setContentView(R.layout.dialog_second_picture);

                                // set the custom dialog components - text, image and button
                                TextView tvMoney = (TextView) dialog.findViewById(R.id.textView_dialog_second_picture_money);
                                tvMoney.setText(Integer.toString(AppInfo.costo_seconda_foto));
                                ImageButton dialogCloseButton = (ImageButton) dialog.findViewById(R.id.imageButton_dialog_second_picture_closeButton);
                                // if button is clicked, close the custom dialog
                                dialogCloseButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                Button btnDialog = (Button) dialog.findViewById(R.id.button_dialog_second_picture_buy);

                                if (AppInfo.getUtente().getMoney() < AppInfo.costo_seconda_foto) {
                                    btnDialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(activity, R.string.not_enough_money, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    // if button is clicked, close the custom dialog
                                    intent.putExtra("price",AppInfo.costo_seconda_foto);
                                    btnDialog.setOnClickListener(new OnClickListenerCamera(intent, requestCode, activity, dialog));
                                }
                                dialog.show();
                            }
                        });
                    }else
                        imageView.setOnClickListener(new OnClickListenerCamera(intent,requestCode,activity));
                }
            }
        }
        dao.close();
    }
}


