package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.C;
import org.mariusconstantin.jobsqueue.bus.IJobQueueEvent;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.JobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.RxJobsConsumer;
import org.mariusconstantin.jobsqueue.executors.UIThreadExecutor;
import org.mariusconstantin.jobsqueue.injectors.DaggerAsyncJobsMockComponent;
import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.producer.IJob;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Marius on 1/26/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class JobsManagerAsyncTest {

    @Module
    @Jobqueue
    public class InjectMockModule {

        @Provides
        @Jobqueue
        public ExecutorService provideJobsExecutor() {
            return BuildConfig.withRxJava ? Executors.newSingleThreadExecutor() :
                    new ThreadPoolExecutor(1,
                            C.NUMBER_OF_THREADS,
                            C.KEEP_ALIVE_TIME,
                            TimeUnit.MILLISECONDS,
                            new PriorityBlockingQueue<Runnable>());
        }

        @SuppressWarnings("ConstantConditions")
        @Provides
        @Jobqueue
        public IJobsConsumer provideConsumer(ExecutorService executorService) {
            return BuildConfig.withRxJava ? new RxJobsConsumer(executorService) : new JobsConsumer(executorService);
        }
    }

    public class MockEventBusListener {
        @WorkerThread
        public void onEventBackgroundThread(IJobQueueEvent event) {
            mCountDownLatch.countDown();
        }
    }

    private MockEventBusListener mMockBusListener;
    private IJobsManager mManager;
    private CountDownLatch mCountDownLatch;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mMockBusListener = spy(new MockEventBusListener());
        mManager = new JobsManager(DaggerAsyncJobsMockComponent.builder().injectMockModule(new InjectMockModule()).build());
        EventBus.getDefault().register(mMockBusListener);
    }

    @Test
    public void testOneSuccessfulJobOnUIThread() {
        mCountDownLatch = new CountDownLatch(1);
        final IJob job = mock(IJob.class);
        mManager.addJob(job);
        try {
            mCountDownLatch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }

        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener).onEventBackgroundThread(any(IJobQueueEvent.class));
        verifyNoMoreInteractions(mMockBusListener);
    }

    @Test
    public void testMultiSuccessfulJobsOnUIThread() {
        final List<IJob> jobs = new ArrayList<>();
        mCountDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final IJob job = mock(IJob.class);
            jobs.add(job);
            mManager.addJob(job);
        }
        try {
            mCountDownLatch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }
        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener, times(10)).onEventBackgroundThread(any(IJobQueueEvent.class));
        verifyNoMoreInteractions(mMockBusListener);
    }

    @After
    public void tearDown() {
        EventBus.getDefault().unregister(mMockBusListener);
    }

}
