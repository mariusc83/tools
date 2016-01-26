package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.producer.IJob;

/**
 * Created by Marius on 1/26/2016.
 */
public interface IJobsManager {

    void addJob(@NonNull IJob job);
}
