package org.mariusconstantin.jobsqueue.consumer;

import org.mariusconstantin.jobsqueue.producer.IJob;

/**
 * Created by MConstantin on 1/27/2016.
 */
public interface IJobsConsumer {

    void execute(IJob job);
}
