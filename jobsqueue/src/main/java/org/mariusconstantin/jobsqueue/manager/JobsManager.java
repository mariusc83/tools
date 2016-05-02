package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.NonNull;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.injectors.IJobsInjectComponent;
import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.producer.IJob;
import javax.inject.Inject;

/**
 * Created by Marius on 1/26/2016.
 */
public class JobsManager implements IJobsManager {

    @Inject
    @Jobqueue
    IJobsConsumer mConsumer;

    public JobsManager(IJobsInjectComponent injectComponent) {
        injectComponent.inject(this);
    }

    @Override
    public void addJob(@NonNull IJob job) {
        mConsumer.execute(job);
    }
}
