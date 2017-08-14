package androidlab.fotografando.assets.sessionList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.MyApp;
import androidlab.fotografando.assets.MySimpleTarget;
import androidlab.fotografando.assets.sessionList.LoadSessionExpirationTask;
import androidlab.fotografando.assets.sessionList.LoadSessionImageTask;

/**
 * Created by miki4 on 27/05/2017.
 */

public class ChallengeSessionAdapter extends BaseAdapter {
    List<ChallengeSession> sessions;
    Context ctx;
    SparseArray<ArrayList<ImageView>> imageViewMap;
    int requestCode;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ChallengeSessionAdapter(List<ChallengeSession> sessions, Context ctx,int requestCode) {
        this.sessions = sessions;
        this.ctx = ctx;
        imageViewMap = new SparseArray<>(sessions.size());
        this.requestCode = requestCode;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public void updateList(SwipeRefreshLayout swipeRefreshLayout){
        //Esegue l'update della lista delle sessioni attraverso un'ulteriore task.
        this.swipeRefreshLayout = swipeRefreshLayout;
        UpdateSessionsTask updateSessionsTask = new UpdateSessionsTask(this);
        updateSessionsTask.execute();
    }
    public void taskResponse(List<ChallengeSession> sessions){
        //Riceve la nuova lista di challengeSessions dal task chiamato in update, e procede con la segnalazione del cambiamento.
        this.sessions = sessions;
        notifyDataSetChanged();
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    static class ViewHolder{
        private TextView data,expiration,title,description;
        private ImageView img1,img2;
        private ConstraintLayout box;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = null;
        ChallengeSession session = sessions.get(position);


        if(convertView == null){
            mViewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.challenge_session_item, parent, false);
            mViewHolder.data = (TextView)convertView.findViewById(R.id.data);
            mViewHolder.expiration = (TextView)convertView.findViewById(R.id.expiration);
            mViewHolder.title = (TextView)convertView.findViewById(R.id.titolo);
            mViewHolder.description = (TextView)convertView.findViewById(R.id.descrizione);
            mViewHolder.box = (ConstraintLayout)convertView.findViewById(R.id.box);
            mViewHolder.img1 = (ImageView)convertView.findViewById(R.id.imgSession1);
            mViewHolder.img2 = (ImageView)convertView.findViewById(R.id.imgSession2);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        mViewHolder.title.setText(session.getTitle());
        mViewHolder.description.setText(session.getDescription());

        imageViewMap.append(session.getIDSession(),new ArrayList<ImageView>(Arrays.asList(mViewHolder.img1, mViewHolder.img2)));

        int myWidth = 300;
        int myHeight = 200;
        URL url = session.getImage();
        Glide.with(MyApp.getAppContext()).load(url).asBitmap().override(300,150).centerCrop().into(
                new MySimpleTarget<Bitmap>(myWidth,myHeight,mViewHolder.box));


        LoadSessionExpirationTask loadExp = new LoadSessionExpirationTask(ctx,session,mViewHolder.expiration);
        loadExp.execute();
        LoadSessionImageTask loadImages = new LoadSessionImageTask(ctx,session,imageViewMap.get(session.getIDSession()),requestCode);
        loadImages.execute();

        return convertView;
    }

    public ImageView getImageView(int sessionId, int pos){
        ImageView res = null;
        try {
            res = imageViewMap.get(sessionId).get(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public ArrayList<ImageView> getImageViews(int sessionId){
        ArrayList<ImageView> res = null;
        try {
            res = imageViewMap.get(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public ChallengeSession getSession(int id){
        ChallengeSession session = null;
        for (ChallengeSession s:sessions){
            if (s.getIDSession() == id)
                session = s;
        }
        return session;
    }
}
