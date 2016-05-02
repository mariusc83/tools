package org.mariusconstantin.jobsqueue.consumer;

/**
 * Created by MConstantin on 1/27/2016.
 */
public interface IRetryPolicy {
    int DEFAULT_NUMBER_OF_RETRIES = 0;

    int maxNumberOfRetries();

}
