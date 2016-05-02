package org.mariusconstantin.jobsqueue.producer;

import android.support.annotation.IntDef;

/**
 * Created by MConstantin on 1/27/2016.
 */
public interface IJob {

    int STATUS_UNKNOWN = 0;
    int STATUS_DONE = 1;
    int STATUS_CANCELED = 2;
    int STATUS_FAIL_THROUGH_EXCEPTION = 3;
    int STATUS_RETRY = 4;
    int STATUS_RETRY_LIMIT_REACHED = 5;
    int STATUS_PENDING = 6;

    @IntDef(value = {STATUS_UNKNOWN, STATUS_DONE, STATUS_CANCELED, STATUS_PENDING, STATUS_FAIL_THROUGH_EXCEPTION, STATUS_RETRY, STATUS_RETRY_LIMIT_REACHED})
    @interface JobStatus {
    }

    void doRun() throws Throwable;

}
