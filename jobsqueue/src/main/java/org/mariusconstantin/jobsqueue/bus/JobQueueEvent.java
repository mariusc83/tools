package org.mariusconstantin.jobsqueue.bus;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.producer.IJob;

/**
 * Created by MConstantin on 1/27/2016.
 */
public class JobQueueEvent implements IJobQueueEvent {
    @NonNull
    private final IJob mJob;

    @IJob.JobStatus
    private final int mStatus;

    private final Throwable mException;

    public JobQueueEvent(@NonNull IJob job, @IJob.JobStatus int status) {
        this(job, status, null);
    }

    public JobQueueEvent(@NonNull IJob job, @IJob.JobStatus int status, Throwable exception) {
        this.mJob = job;
        this.mStatus = status;
        this.mException = exception;
    }

    @Override
    public IJob getJob() {
        return mJob;
    }

    @Override
    public int getStatus() {
        return mStatus;
    }

    @Override
    public Throwable getException() {
        return mException;
    }
}
