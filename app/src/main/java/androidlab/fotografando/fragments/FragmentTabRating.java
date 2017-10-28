package androidlab.fotografando.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

import androidlab.DB.Objects.Photo;
import androidlab.DB.Objects.Rating;
import androidlab.DB.Objects.Utente;
import androidlab.fotografando.R;
import androidlab.fotografando.assets.adapters.AdapterRatingPhotos;
import androidlab.fotografando.assets.objects.AppInfo;
import androidlab.fotografando.assets.tasks.AsyncTaskLoaderLoadRatings;
import androidlab.fotografando.assets.tasks.TaskInsertRating;

/**
 * Created by miki4 on 11/09/2017.
 */

public class FragmentTabRating extends Fragment implements LoaderManager.LoaderCallbacks<List<Photo>> {
    public AdapterRatingPhotos adapter;
    private RecyclerView rvRatings;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Collego layout a fragment
        View view = inflater.inflate(R.layout.fragment_rating_tab, container, false);
        //RelativeLayout tabRating = (RelativeLayout) view.findViewById(R.id.tabRating);

        //Avvio task per il caricamento delle foto
        /*TaskRatingLoadPhoto taskRatingLoadPhoto = new TaskRatingLoadPhoto(getActivity().getApplicationContext(),tabRating,getActivity());
        taskRatingLoadPhoto.execute();*/
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRatings = (RecyclerView) getView().findViewById(R.id.recyclerView_fragment_ratings);


        // Set layout manager to position the items
        rvRatings.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new AdapterRatingPhotos(getContext(),getActivity());
        // Attach the adapter to the recyclerview to populate items
        rvRatings.setAdapter(adapter);
        // Set layout manager to position the items
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvRatings.setLayoutManager(layoutManager);

        rvRatings.setHasFixedSize(true);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvRatings);

        //Rimozione ed invio voto
        rvRatings.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    int pos = layoutManager.getPosition(centerView);
                    if(pos != 0){
                        CardView card = (CardView)recyclerView.getLayoutManager().findViewByPosition(0);
                        float voto = ((RatingBar)card.findViewById(R.id.ratingBar)).getRating();
                        Rating rating = new Rating(adapter.getItems().get(0),AppInfo.getUtente(),Math.round(voto),false);
                        adapter.removeItem(0);
                        TaskInsertRating insertRating = new TaskInsertRating(rating);
                        insertRating.execute();
                        //Se voto non è nullo allora dò dei soldi
                        if(rating.getVoto() != 0){
                            Utente user = AppInfo.getUtente();
                            user.setMoney(AppInfo.retribuzione_votazione,true);
                            AppInfo.updateUtente(user);
                            Toast.makeText(getContext(), "1 dollar earned!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        progressBar =(ProgressBar) getView().findViewById(R.id.progressBar_fragment_ratings);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<List<Photo>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderLoadRatings(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Photo>> loader, List<Photo> data) {
        adapter.setItems(data);
        progressBar.setVisibility(ProgressBar.GONE);
            }

    @Override
    public void onLoaderReset(Loader<List<Photo>> loader) {

    }
}
