package androidlab.fotografando.assets.sessionList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
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

/**
 * Created by miki4 on 27/05/2017.
 */

/** Adapter per la lista delle challenges **/
public class ChallengeSessionAdapter extends BaseAdapter {
    private List<ChallengeSession> sessions;
    private Context ctx;
    private SparseArray<ArrayList<ImageView>> imageViewMap;
    private int requestCode;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentActivity activity;

    public ChallengeSessionAdapter(List<ChallengeSession> sessions, Context ctx,int requestCode,FragmentActivity activity,SwipeRefreshLayout swipeRefreshLayout) {
        this.sessions = sessions;
        this.ctx = ctx;
        imageViewMap = new SparseArray<>(sessions.size());
        this.requestCode = requestCode;
        this.activity = activity;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((ChallengeSession)getItem(position)).getIDSession();
    }
    public void updateList(){
        //Esegue l'update della lista delle sessioni attraverso un'ulteriore task.
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
        //Notifica cambio dati e ferma il refresh
        super.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    /** viewholder per il riciclo delle view **/
    private static class ViewHolder{
        private TextView data,expiration,title,description;
        private ImageView img1,img2;
        private ConstraintLayout box;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        ChallengeSession session = (ChallengeSession)getItem(position);

        if(convertView == null){
            //Instazio nuova view
            mViewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_challenge_session, parent, false);
            mViewHolder.data = (TextView)convertView.findViewById(R.id.data);
            mViewHolder.expiration = (TextView)convertView.findViewById(R.id.expiration);
            mViewHolder.title = (TextView)convertView.findViewById(R.id.titolo);
            mViewHolder.description = (TextView)convertView.findViewById(R.id.descrizione);
            mViewHolder.box = (ConstraintLayout)convertView.findViewById(R.id.box);
            mViewHolder.img1 = (ImageView)convertView.findViewById(R.id.imgSession1);
            mViewHolder.img2 = (ImageView)convertView.findViewById(R.id.imgSession2);
            convertView.setTag(mViewHolder);
        }else{
            //Riciclo la view
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        mViewHolder.title.setText(session.getTitle());
        mViewHolder.description.setText(session.getDescription());

        //Mi segno le due imageView per poterle rintracciare dopo
        imageViewMap.append(session.getIDSession(),new ArrayList<>(Arrays.asList(mViewHolder.img1, mViewHolder.img2)));

        //Carico sfondo della sessione
        int myWidth = 300;
        int myHeight = 150;//200
        URL url = session.getImage();
        Glide.with(MyApp.getAppContext()).load(url).asBitmap().override(300,150 /*150*/).centerCrop().into(
                new MySimpleTarget<Bitmap>(myWidth,myHeight,mViewHolder.box));


        //Carico in maniera asincrona le scadenze delle sessioni e le immagini dell'utente
        LoadSessionExpirationTask loadExp = new LoadSessionExpirationTask(ctx,session,mViewHolder.expiration);
        loadExp.execute();
        LoadSessionImageTask loadImages = new LoadSessionImageTask(ctx,session,imageViewMap.get(session.getIDSession()),requestCode,activity);
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
