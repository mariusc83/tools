package org.mariusconstantin.jobsqueue.producer;

import android.support.annotation.IntDef;

/**
 * Created by MConstantin on 1/27/2016.
 */
public interface IJob {

    int STATUS_UNKNOWN = 0;
    int STATUS_DONE = 1;
    int STATUS_CANCELED = 2;
    int STATUS_PENDING = 3;


    @IntDef(value = {STATUS_UNKNOWN, STATUS_DONE, STATUS_CANCELED, STATUS_PENDING})
    @interface JobStatus {
    }

    void doRun() throws Throwable;
}
