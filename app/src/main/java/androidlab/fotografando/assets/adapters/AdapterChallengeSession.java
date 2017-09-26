package androidlab.fotografando.assets.sessionList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidlab.DB.Objects.ChallengeSession;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.ImageViewChallenge;
import androidlab.fotografando.assets.MyApp;
import androidlab.fotografando.assets.MySimpleTarget;

/**
 * Created by miki4 on 27/05/2017.
 */

/** Adapter per la lista delle challenges **/
public class ChallengeSessionAdapter extends RecyclerView.Adapter<ChallengeSessionAdapter.ViewHolder> {




    private List<ChallengeSession> sessions;
    private Context context;
    private SparseArray<ArrayList<ImageViewChallenge>> imageViewMap;
    private int requestCode;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentActivity activity;

    public List<ChallengeSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<ChallengeSession> sessions) {
        this.sessions = sessions;
        imageViewMap = new SparseArray<>(sessions.size());
    }


    public ChallengeSessionAdapter(Context context, int requestCode, SwipeRefreshLayout swipeRefreshLayout, FragmentActivity activity) {
        this.context = context;
        this.requestCode = requestCode;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.activity = activity;
        sessions = new ArrayList<>();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView data,expiration,title,description;
        public ImageViewChallenge img1,img2;
        public ConstraintLayout box;
        public ProgressBar progressBar1,progressBar2;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            data = (TextView) itemView.findViewById(R.id.data);
            expiration = (TextView) itemView.findViewById(R.id.expiration);
            title = (TextView) itemView.findViewById(R.id.titolo);
            description = (TextView) itemView.findViewById(R.id.descrizione);
            img1 = (ImageViewChallenge) itemView.findViewById(R.id.imgSession1);
            img2 = (ImageViewChallenge) itemView.findViewById(R.id.imgSession2);
            box = (ConstraintLayout) itemView.findViewById(R.id.box);
            progressBar1 = (ProgressBar) itemView.findViewById(R.id.progressBar_item_challenge_img1);
            progressBar2 = (ProgressBar) itemView.findViewById(R.id.progressBar_item_challenge_img2);
        }
    }

    @Override
    public ChallengeSessionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View challengeView = inflater.inflate(R.layout.item_challenge_session, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(challengeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChallengeSession session = sessions.get(position);

        // Set item views based on your views and data model
        TextView tvTitle = holder.title;
        tvTitle.setText(session.getTitle());

        TextView tvDesc = holder.description;
        tvDesc.setText(session.getDescription());

        //Mi segno le due imageView per poterle rintracciare dopo
        imageViewMap.append(session.getIDSession(),new ArrayList<>(Arrays.asList(holder.img1, holder.img2)));

        //Carico sfondo della sessione
        int myWidth = 300;
        int myHeight = 150;
        URL url = session.getImage();
        Glide.with(MyApp.getAppContext()).load(url).asBitmap().override(300,150).centerCrop().into(
                new MySimpleTarget<Bitmap>(myWidth,myHeight,holder.box));

        ProgressBar progressBar1 = holder.progressBar1;
        ProgressBar progressBar2 = holder.progressBar2;

        progressBar1.setVisibility(ProgressBar.VISIBLE);
        progressBar2.setVisibility(ProgressBar.VISIBLE);
        //Carico in maniera asincrona le scadenze delle sessioni e le immagini dell'utente
        LoadSessionExpirationTask loadExp = new LoadSessionExpirationTask(context,session,holder.expiration);
        loadExp.execute();
        LoadSessionImageTask loadImages = new LoadSessionImageTask(context,session,imageViewMap.get(session.getIDSession()),requestCode,activity,progressBar1,progressBar2);
        loadImages.execute();

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    private Context getContext() {
        return context;
    }

    public ImageViewChallenge getImageView(int sessionId, int pos){
        ImageViewChallenge res = null;
        try {
            res = imageViewMap.get(sessionId).get(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public ArrayList<ImageViewChallenge> getImageViews(int sessionId){
        ArrayList<ImageViewChallenge> res = null;
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
