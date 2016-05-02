package org.mariusconstantin.jobsqueue.consumer;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.producer.IJob;
import org.mariusconstantin.jobsqueue.producer.JobsHolder;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by MConstantin on 1/27/2016.
 */

public class JobsConsumer implements IJobsConsumer {

    @NonNull
    @Jobqueue
    private final ExecutorService mExecutorService;


    @Inject
    public JobsConsumer(@NonNull ExecutorService executorService) {
        mExecutorService = executorService;
    }

    @Override
    public void execute(IJob job) {
        mExecutorService.execute(JobsHolder.encapsulate(job));
    }

}
