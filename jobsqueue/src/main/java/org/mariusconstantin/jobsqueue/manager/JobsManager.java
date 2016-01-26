package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.NonNull;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.injectors.IInjectComponent;
import org.mariusconstantin.jobsqueue.producer.IJob;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Marius on 1/26/2016.
 */
public class JobsManager implements IJobsManager {

    @Inject
    @Singleton
    IJobsConsumer mConsumer;

    public JobsManager(IInjectComponent injectComponent) {
        injectComponent.inject(this);
    }

    @Override
    public void addJob(@NonNull IJob job) {
        mConsumer.execute(job);
    }
}
