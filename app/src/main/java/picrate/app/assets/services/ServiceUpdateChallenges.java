package picrate.app.assets.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import picrate.app.assets.adapters.AdapterChallengeSession;
import picrate.app.assets.tasks.TaskUpdateSessionsList;
import picrate.app.fragments.FragmentTabChallenge;

/**
 * Created by Michele Grisafi on 29/11/2017.
 */

public class ServiceUpdateChallenges extends JobService {
    private TaskUpdateSessionsList task;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        task = new TaskUpdateSessionsList(this,jobParameters);
        task.execute();
        return true;
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        task.cancel(true);
        return false;
    }
}
