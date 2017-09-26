package androidlab.fotografando.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidlab.fotografando.R;
import androidlab.fotografando.assets.tasks.TaskRatingLoadPhoto;

/**
 * Created by miki4 on 11/09/2017.
 */

public class FragmentTabRating extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Collego layout a fragment
        View view = inflater.inflate(R.layout.fragment_rating_tab, container, false);
        RelativeLayout tabRating = (RelativeLayout) view.findViewById(R.id.tabRating);

        //Avvio task per il caricamento delle foto
        TaskRatingLoadPhoto taskRatingLoadPhoto = new TaskRatingLoadPhoto(getActivity().getApplicationContext(),tabRating,getActivity());
        taskRatingLoadPhoto.execute();
        return view;
    }
}
