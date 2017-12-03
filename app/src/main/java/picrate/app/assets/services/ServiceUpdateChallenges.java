package picrate.app.assets.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import picrate.app.fragments.FragmentTabChallenge;

/**
 * Created by Michele Grisafi on 29/11/2017.
 */

public class ServiceUpdateChallenges extends JobService {
    FragmentTabChallenge activity;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        activity.getLoaderManager().restartLoader(0,null,activity).forceLoad();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        activity.getLoaderManager().destroyLoader(0);
        return false;
    }
}
