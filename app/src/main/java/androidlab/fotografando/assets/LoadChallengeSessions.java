package androidlab.fotografando.assets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidlab.DB.DAO.ChallengeDAO;
import androidlab.DB.DAO.ChallengeSessionDAO;
import androidlab.DB.DAO.implementations.ChallengeSessionDAO_DB_impl;
import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;

/**
 * Created by Michele Grisafi on 14/05/2017.
 */

public class LoadChallengeSessions extends AsyncTask<Void,Void,List<ChallengeSession>> {
    Context context;
    RelativeLayout layout;
    List<ChallengeSession> result;
    Map<Integer,Integer> pictureMap;
    Map<Integer,Integer> expirationMap;

    LinearLayout block;

    public LoadChallengeSessions(Context context,RelativeLayout layout, List<ChallengeSession> result, Map<Integer,Integer> pictureMap,Map<Integer,Integer> expirationMap){
        this.context = context;
        this.result = result;
        this.layout = layout;
        this.pictureMap = pictureMap;
        this.expirationMap = expirationMap;
    }
    @Override
    protected List<ChallengeSession> doInBackground(Void... params) {
        ChallengeSessionDAO sessionDAO = new ChallengeSessionDAO_DB_impl();
        sessionDAO.open();
        result = sessionDAO.getActiveSessions();
        return result;
    }

    @Override
    protected void onPostExecute(List<ChallengeSession> challengeSessions) {
        super.onPostExecute(challengeSessions);
        Collections.sort(challengeSessions);
        List<Integer> idExpiration = new ArrayList<Integer>();
        List<Integer> ids = new ArrayList<Integer>();

        for (int j = 0; j < challengeSessions.size(); j++){
            TextView data = new TextView(context);
            data.setText("data");
            data.setId(View.generateViewId());
            data.setTextColor(context.getResources().getColor(R.color.colorBlack));
            ids.add(data.getId());
            RelativeLayout.LayoutParams dataParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (data.getId() != ids.get(0)) {
                dataParam.addRule(RelativeLayout.BELOW, idExpiration.get(idExpiration.size()-1));
            }
            dataParam.setMargins(0, AppInfo.dpToPixel(8),0,0);
            data.setLayoutParams(dataParam);

            block = new LinearLayout(context);
            block.setId(View.generateViewId());
            block.setOrientation(LinearLayout.HORIZONTAL);
            block.setWeightSum(3);
            RelativeLayout.LayoutParams linearLayoutParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutParam.addRule(RelativeLayout.BELOW, data.getId());
            block.setLayoutParams(linearLayoutParam);

            int myWidth = 512;
            int myHeight = 384;
            URL url = challengeSessions.get(j).getImage();

            Glide.with(MyApp.getAppContext()).load(url).asBitmap().into(
                    new MySimpleTarget<Bitmap>(myWidth,myHeight,block.getId(),layout));

            RelativeLayout firstHalf = new RelativeLayout(context);
            firstHalf.setId(View.generateViewId());
            //firstHalf.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            LinearLayout.LayoutParams firstHalfLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);
            firstHalf.setLayoutParams(firstHalfLayoutParam);

            TextView title = new TextView(context);
            title.setText(challengeSessions.get(j).getTitle());
            title.setId(View.generateViewId());
            RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleParams.setMargins(AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),0,0);
            title.setLayoutParams(titleParams);

            TextView desc = new TextView(context);
            desc.setText(challengeSessions.get(j).getDescription());
            desc.setId(View.generateViewId());
            RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            descParams.setMargins(AppInfo.dpToPixel(8),AppInfo.dpToPixel(8),0,AppInfo.dpToPixel(8));
            descParams.addRule(RelativeLayout.BELOW, title.getId());
            desc.setLayoutParams(descParams);

            RelativeLayout secondHalf = new RelativeLayout(context);
            secondHalf.setId(View.generateViewId());
            //secondHalf.setBackgroundColor(context.getResources().getColor(R.color.wallet_holo_blue_light));
            LinearLayout.LayoutParams secondHalfLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,2);
            secondHalf.setLayoutParams(secondHalfLayoutParam);

            ImageView picture = new ImageView(context);
            picture.setId(View.generateViewId());
            picture.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_no_picture, null));
            RelativeLayout.LayoutParams pictureParams = new RelativeLayout.LayoutParams(AppInfo.dpToPixel(70),AppInfo.dpToPixel(70));
            pictureParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            pictureParams.setMargins(0,AppInfo.dpToPixel(8),0,0);
            picture.setLayoutParams(pictureParams);

            pictureMap.put(challengeSessions.get(j).getIDSession(),picture.getId());

            ImageButton addPhoto = new ImageButton(context);
            addPhoto.setId(View.generateViewId());
            addPhoto.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            addPhoto.setBackgroundResource(0);
            RelativeLayout.LayoutParams addPhotoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            addPhotoParams.addRule(RelativeLayout.BELOW,picture.getId());
            addPhotoParams.addRule(RelativeLayout.ALIGN_RIGHT,picture.getId());
            addPhotoParams.setMargins(0,AppInfo.dpToPixel(8),0,AppInfo.dpToPixel(8));
            addPhoto.setLayoutParams(addPhotoParams);


            TextView expiration = new TextView(context);
            expiration.setText(R.string.expiresIn);
            expiration.setId(View.generateViewId());
            expiration.setGravity(Gravity.RIGHT);
            expiration.setTextColor(context.getResources().getColor(R.color.colorBlack));
            RelativeLayout.LayoutParams expirationParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            expirationParam.addRule(RelativeLayout.BELOW,block.getId());
            expiration.setLayoutParams(expirationParam);
            expirationMap.put(challengeSessions.get(j).getIDSession(),expiration.getId());
            idExpiration.add(expiration.getId());

            layout.addView(data);
            layout.addView(block);
            block.addView(firstHalf);
            block.addView(secondHalf);
            firstHalf.addView(title);
            firstHalf.addView(desc);
            secondHalf.addView(picture);
            secondHalf.addView(addPhoto);
            layout.addView(expiration);
        }
        LoadSessionsExpiration loadExp = new LoadSessionsExpiration(context,expirationMap,challengeSessions,layout);
        loadExp.execute();
        LoadSessionsImages loadImages = new LoadSessionsImages(context,layout,challengeSessions,pictureMap);
        loadImages.execute();
    }
}
