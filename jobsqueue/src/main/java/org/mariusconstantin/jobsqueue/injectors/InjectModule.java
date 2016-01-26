package org.mariusconstantin.jobsqueue.injectors;

import android.support.annotation.NonNull;
import org.mariusconstantin.jobsqueue.C;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.JobsConsumer;
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
@Singleton
public class InjectModule {

    @Provides
    @Singleton
    public ExecutorService provideJobsExecutor() {
        return new ThreadPoolExecutor(1,
                C.NUMBER_OF_THREADS,
                C.KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>());
    }


    @Provides
    @Singleton
    public IJobsConsumer provideConsumer(@NonNull ExecutorService service) {
        return new JobsConsumer(service);
    }
}
