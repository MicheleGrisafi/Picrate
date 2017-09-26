package androidlab.fotografando.assets.OLD;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidlab.DB.DAO.ChallengeSessionDAO;
import androidlab.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.objects.MyApp;
import androidlab.fotografando.assets.objects.MySimpleTarget;

/**
 * Created by Michele Grisafi on 14/05/2017.
 */

public class OLD_LoadChallengeSessions extends AsyncTask<Void,Void,List<ChallengeSession>> {
    Context context;
    RelativeLayout layout;
    List<ChallengeSession> result;
    SparseArray<ArrayList<Integer>> pictureMap;
    SparseIntArray expirationMap;
    int requestCode;



    public OLD_LoadChallengeSessions(Context context, RelativeLayout layout, List<ChallengeSession> result, SparseArray<ArrayList<Integer>> pictureMap, SparseIntArray expirationMap, int requestCode){
        this.context = context;
        this.result = result;
        this.layout = layout;
        this.pictureMap = pictureMap;
        this.expirationMap = expirationMap;
        this.requestCode = requestCode;
        this.requestCode = requestCode;
    }
    @Override
    protected List<ChallengeSession> doInBackground(Void... params) {
        ChallengeSessionDAO sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
        result = sessionDAO.getActiveSessions();
        return result;
    }

    @Override
    protected void onPostExecute(final List<ChallengeSession> challengeSessions) {
        super.onPostExecute(challengeSessions);
        Collections.sort(challengeSessions);
        List<Integer> ids = new ArrayList<Integer>();
        ChallengeSession thisChallenge;

        /*********************************+ CREAZIONE DEL LAYOUT ************************************************/
        for (int j = 0; j < challengeSessions.size(); j++){
            thisChallenge = challengeSessions.get(j);

            /**
             * SCHEMA =
             * CARD
             *      DATA
             *      BOX
             *          TITOLO
             *          DESCRIZIONE
             *          IMMAGINE 1
             *          IMMAGINE 2
             *      EXPIRATION
             *
             */



            /********************************** CARD **********************************************/
            CardView card = new CardView(context);
            card.setId(View.generateViewId());
            card.setRadius(AppInfo.dpToPixel(10));
            card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
            RelativeLayout.LayoutParams cardParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (j != 0) {
                cardParams.addRule(RelativeLayout.BELOW, ids.get(j-1));
            }
            if (j == challengeSessions.size() -1)
                cardParams.setMargins(AppInfo.dpToPixel(16),AppInfo.dpToPixel(16),AppInfo.dpToPixel(16),AppInfo.dpToPixel(16));
            else
                cardParams.setMargins(AppInfo.dpToPixel(16),AppInfo.dpToPixel(16),AppInfo.dpToPixel(16),0);
            card.setLayoutParams(cardParams);
            ids.add(card.getId());

            /******************************** CONTAINER DEL CONTENUTO DELLA CARD **********************/
            RelativeLayout container = new RelativeLayout(context);
            container.setId(View.generateViewId());
            RelativeLayout.LayoutParams containerParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            container.setLayoutParams(containerParam);

            /******************************** DATA DELLA CHALLENGE **********************/
            TextView data = new TextView(context);
            data.setText("data");
            data.setId(View.generateViewId());
            data.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            RelativeLayout.LayoutParams dataParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dataParam.setMargins(AppInfo.dpToPixel(8), AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),AppInfo.dpToPixel(8));
            data.setLayoutParams(dataParam);

            /******************************** SFONDO, NONCHè CONTENITORE DEI DATI, DELLA CHALLENGE - NON CONTIENE DATA ED EXPIRATION **********************/
            ConstraintLayout box = new ConstraintLayout(context);
            box.setId(View.generateViewId());
            RelativeLayout.LayoutParams boxParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            boxParams.addRule(RelativeLayout.BELOW,data.getId());
            box.setLayoutParams(boxParams);

            /******************************** TITOLO **********************/
            TextView title = new TextView(context);
            title.setText(thisChallenge.getTitle());
            title.setId(View.generateViewId());
            title.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            ConstraintLayout.LayoutParams titleParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleParams.setMargins(AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),0);
            title.setLayoutParams(titleParams);

            /******************************** DESCRIZIONE **********************/
            TextView desc = new TextView(context);
            desc.setText(thisChallenge.getDescription());
            desc.setId(View.generateViewId());
            desc.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            ConstraintLayout.LayoutParams descParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            descParams.setMargins(AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),0);
            desc.setLayoutParams(descParams);

            /******************************** IMMAGINE **********************/
            ImageView picture = new ImageView(context);
            picture.setId(View.generateViewId());
            picture.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            ConstraintLayout.LayoutParams pictureParams = new ConstraintLayout.LayoutParams(AppInfo.dpToPixel(50),AppInfo.dpToPixel(50));
            pictureParams.setMargins(AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),0);
            picture.setLayoutParams(pictureParams);

            /******************************** IMMAGINE **********************/
            ImageView picture2 = new ImageView(context);
            picture2.setId(View.generateViewId());
            picture2.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            ConstraintLayout.LayoutParams pictureParams2 = new ConstraintLayout.LayoutParams(AppInfo.dpToPixel(50),AppInfo.dpToPixel(50));
            pictureParams2.setMargins(0,AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),0);
            picture2.setLayoutParams(pictureParams2);


            /******************************** SCADENZA **********************/
            TextView expiration = new TextView(context);
            expiration.setText(R.string.expiresIn);
            expiration.setId(View.generateViewId());
            expiration.setGravity(Gravity.RIGHT);
            expiration.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            RelativeLayout.LayoutParams expirationParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            expirationParam.addRule(RelativeLayout.BELOW,box.getId());
            expirationParam.setMargins(AppInfo.dpToPixel(8), AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),AppInfo.dpToPixel(8));
            expiration.setLayoutParams(expirationParam);


            /******************************** AGGIUUNGO VIEW ALLA ACTIVITY **********************/
            container.addView(expiration);
            box.addView(picture2);
            box.addView(picture);
            box.addView(desc);
            box.addView(title);
            container.addView(box);
            container.addView(data);
            card.addView(container);
            layout.addView(card);


            /*************************************** CONSTRAINT set ***************************************/
            ConstraintSet set = new ConstraintSet();
            set.clone(box);
            set.connect(title.getId(),ConstraintSet.TOP,box.getId(),ConstraintSet.TOP);
            set.connect(title.getId(),ConstraintSet.LEFT,box.getId(),ConstraintSet.LEFT);
            set.connect(title.getId(),ConstraintSet.RIGHT,box.getId(),ConstraintSet.RIGHT);
            set.connect(desc.getId(),ConstraintSet.TOP,title.getId(),ConstraintSet.BOTTOM);
            set.connect(desc.getId(),ConstraintSet.LEFT,box.getId(),ConstraintSet.LEFT);
            set.connect(desc.getId(),ConstraintSet.RIGHT,box.getId(),ConstraintSet.RIGHT);
            set.connect(picture.getId(),ConstraintSet.TOP,desc.getId(),ConstraintSet.BOTTOM);
            set.connect(picture2.getId(),ConstraintSet.TOP,desc.getId(),ConstraintSet.BOTTOM);
            set.connect(picture.getId(),ConstraintSet.BOTTOM,box.getId(),ConstraintSet.BOTTOM);
            set.connect(picture2.getId(),ConstraintSet.BOTTOM,box.getId(),ConstraintSet.BOTTOM);
            set.createHorizontalChain(box.getId(),ConstraintSet.LEFT,box.getId(),ConstraintSet.RIGHT,new int[]{picture.getId(),picture2.getId()},new float[]{1,1},ConstraintSet.CHAIN_PACKED);
            set.applyTo(box);

            /******************************** NON TOCCARE ->  struttura dati, operazioni **********************/
            expirationMap.put(thisChallenge.getIDSession(),expiration.getId());
            ArrayList<Integer> imageViewList = new ArrayList<>(2);
            imageViewList.add(picture.getId());
            imageViewList.add(picture2.getId());
            pictureMap.put(thisChallenge.getIDSession(),imageViewList);
            int myWidth = 300;
            int myHeight = 200;
            URL url = thisChallenge.getImage();
            Glide.with(MyApp.getAppContext()).load(url).asBitmap().override(300,150).centerCrop().into(
                    new MySimpleTarget<Bitmap>(myWidth,myHeight,box.getId(),layout));
        }
        OLD_LoadSessionsExpiration loadExp = new OLD_LoadSessionsExpiration(context,expirationMap,challengeSessions,layout);
        loadExp.execute();
        OLD_LoadSessionsImages loadImages = new OLD_LoadSessionsImages(context,layout,challengeSessions,pictureMap,requestCode);
        loadImages.execute();
    }
}
