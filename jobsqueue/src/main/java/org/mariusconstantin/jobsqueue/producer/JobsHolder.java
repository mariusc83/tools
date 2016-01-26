package org.mariusconstantin.jobsqueue.producer;

import android.support.annotation.NonNull;
import org.mariusconstantin.jobsqueue.bus.JobQueueEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by MConstantin on 1/27/2016.
 */
public class JobsHolder implements Runnable {
    private final IJob mJob;

    private JobsHolder(@NonNull IJob job) {
        this.mJob = job;
    }

    @Override
    public void run() {
        try {
            mJob.doRun();
            EventBus.getDefault().postSticky(new JobQueueEvent(mJob, IJob.STATUS_DONE));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public static JobsHolder encapsulate(@NonNull IJob job) {
        return new JobsHolder(job);
    }
}
