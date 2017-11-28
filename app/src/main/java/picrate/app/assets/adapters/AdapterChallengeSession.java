package androidlab.app.assets.adapters;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidlab.app.DB.Objects.ChallengeSession;
import androidlab.app.R;
import androidlab.app.assets.views.ImageViewChallenge;
import androidlab.app.assets.objects.MyApp;
import androidlab.app.assets.objects.MySimpleTarget;
import androidlab.app.assets.tasks.TaskLoadSessionExpiration;
import androidlab.app.assets.tasks.TaskLoadSessionImage;

/**
 * Created by miki4 on 27/05/2017.
 */

/** Adapter per la lista delle challenges **/
public class AdapterChallengeSession extends RecyclerView.Adapter<AdapterChallengeSession.ViewHolder> {

    private List<ChallengeSession> sessions;
    private Context context;
    private SparseArray<ArrayList<ImageViewChallenge>> imageViewMap;
    private SparseArray<ArrayList<ProgressBar>> progressBarMap;
    private int requestCode;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentActivity activity;

    public List<ChallengeSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<ChallengeSession> sessions) {
        this.sessions = sessions;
        imageViewMap = new SparseArray<>();
        progressBarMap = new SparseArray<>();
        notifyDataSetChanged();
    }


    public AdapterChallengeSession(Context context, int requestCode, SwipeRefreshLayout swipeRefreshLayout,
                                   FragmentActivity activity) {
        this.context = context;
        this.requestCode = requestCode;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.activity = activity;
        this.sessions = new ArrayList<>();
        imageViewMap = new SparseArray<>();
        progressBarMap = new SparseArray<>();
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
    public AdapterChallengeSession.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        imageViewMap.put(session.getIDSession(),new ArrayList<>(Arrays.asList(holder.img1, holder.img2)));
        //Mi segno le progress bar per passarle al task LoadSessionImage al di fuori dell'adapter
        progressBarMap.put(session.getIDSession(),new ArrayList<>(Arrays.asList(holder.progressBar1,holder.progressBar2)));
        //Carico sfondo della sessione
        //imageViewMap2 = imageViewMap.clone();

        int myWidth = 300;
        int myHeight = 150;
        URL url = session.getImage();
        Glide.with(MyApp.getAppContext()).load(url).asBitmap().override(300,150).centerCrop().into(
                new MySimpleTarget<Bitmap>(myWidth,myHeight,holder.box));





        //Carico in maniera asincrona le scadenze delle sessioni e le immagini dell'utente
        TaskLoadSessionExpiration loadExp = new TaskLoadSessionExpiration(context,session,holder.expiration);
        loadExp.execute();
        TaskLoadSessionImage loadImages = new TaskLoadSessionImage(context,session,imageViewMap.get(session.getIDSession()),requestCode,activity,holder.progressBar1,holder.progressBar2);
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
    public ArrayList<ProgressBar> getProgressBars(int sessionId){
        ArrayList<ProgressBar> res = null;
        try {
            res = progressBarMap.get(sessionId);
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
