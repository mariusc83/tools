package org.mariusconstantin.jobsqueue.injectors;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.C;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.JobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.RxJobsConsumer;
import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Marius on 1/26/2016.
 */


@Module
@Jobqueue
public class JobsInjectModule {

    @Provides
    @Jobqueue
    public ExecutorService provideJobsExecutor() {
        return new ThreadPoolExecutor(1,
                C.NUMBER_OF_THREADS,
                C.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>());
    }


    @SuppressWarnings("ConstantConditions")
    @Provides
    @Jobqueue
    public IJobsConsumer provideConsumer(@NonNull ExecutorService service) {
        return BuildConfig.withRxJava ? new RxJobsConsumer(service) : new JobsConsumer(service);
    }
}
