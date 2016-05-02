package org.mariusconstantin.jobsqueue.consumer;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.bus.JobQueueEvent;
import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.producer.IJob;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.exceptions.Exceptions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by MConstantin on 2/3/2016.
 */
public class RxJobsConsumer implements IJobsConsumer, Observer<IJob> {
    @NonNull
    @Jobqueue
    private final ExecutorService mExecutorService;
    @NonNull
    private final Scheduler mScheduler;

    @Inject
    public RxJobsConsumer(@NonNull ExecutorService executorService) {
        mExecutorService = executorService;
        mScheduler = Schedulers.from(executorService);
    }

    @Override
    public void execute(IJob job) {
        Observable.just(job)
                .map(new Func1<IJob, IJob>() {
                    @Override
                    public IJob call(IJob job) {
                        try {
                            job.doRun();
                            return job;
                        } catch (Throwable e) {
                            throw Exceptions.propagate(e);
                        }
                    }
                })
                .subscribeOn(mScheduler)
                .observeOn(mScheduler)
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(IJob job) {
        EventBus.getDefault().postSticky(new JobQueueEvent(job, IJob.STATUS_DONE));
    }
}
