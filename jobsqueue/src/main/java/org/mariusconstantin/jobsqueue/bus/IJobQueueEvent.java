package org.mariusconstantin.jobsqueue.bus;

import android.support.annotation.IntDef;

import org.mariusconstantin.jobsqueue.producer.IJob;

/**
 * Created by MConstantin on 1/27/2016.
 */
public interface IJobQueueEvent {

    IJob getJob();

    @IJob.JobStatus int getStatus();
}
