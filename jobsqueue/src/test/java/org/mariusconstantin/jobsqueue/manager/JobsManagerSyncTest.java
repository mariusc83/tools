package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.MainThread;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.bus.IJobQueueEvent;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.JobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.RxJobsConsumer;
import org.mariusconstantin.jobsqueue.executors.UIThreadExecutor;
import org.mariusconstantin.jobsqueue.injectors.DaggerSyncJobsMockComponent;
import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.producer.IJob;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Marius on 1/26/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class JobsManagerSyncTest {

    @Module
    @Jobqueue
    public class InjectMockModule {

        @Provides
        @Jobqueue
        public ExecutorService provideJobsExecutor() {
            return new UIThreadExecutor();
        }

        @SuppressWarnings("ConstantConditions")
        @Provides
        @Jobqueue
        public IJobsConsumer provideConsumer(ExecutorService executorService) {
            return BuildConfig.withRxJava ? new RxJobsConsumer(executorService) : new JobsConsumer(executorService);
        }
    }

    public static class MockEventBusListener {
        @MainThread
        public void onEventMainThread(IJobQueueEvent event) {
        }
    }

    @Mock
    private MockEventBusListener mMockBusListener;
    @Mock
    private IJob mMockJob1;

    private IJobsManager mManager;

    // argument captors
    private final ArgumentCaptor<IJobQueueEvent> mArgumentCaptor = ArgumentCaptor.forClass(IJobQueueEvent.class);

    // exceptions
    private final Exception mException1 = new Exception("Test exception message");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mManager = new JobsManager(DaggerSyncJobsMockComponent.builder().injectMockModule(new InjectMockModule()).build());
        EventBus.getDefault().register(mMockBusListener);
    }

    @Test
    public void testOneSuccessfulJobOnUIThread() {
        mManager.addJob(mMockJob1);
        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener).onEventMainThread(any(IJobQueueEvent.class));
        verifyNoMoreInteractions(mMockBusListener);
    }

    @Test
    public void testOneUnSuccessfulJobOnUIThread() {
        try {
            doThrow(mException1).when(mMockJob1).doRun();
        } catch (Throwable e) {
        }
        mManager.addJob(mMockJob1);
        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener).onEventMainThread(mArgumentCaptor.capture());
        assertThat(mArgumentCaptor.getValue().getStatus()).isEqualTo(IJob.STATUS_FAIL_THROUGH_EXCEPTION);
        assertThat(mArgumentCaptor.getValue().getException().getMessage()).isEqualTo(mException1.getMessage());
        verifyNoMoreInteractions(mMockBusListener);
    }

    @Test
    public void testOneSuccessfulJobWithRetryOnUIThread() {
        try {
            doThrow(mException1).doNothing().when(mMockJob1).doRun(); // second time it works
        } catch (Throwable e) {
        }
        mManager.addJob(mMockJob1);
        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener).onEventMainThread(mArgumentCaptor.capture());
        assertThat(mArgumentCaptor.getValue().getStatus()).isEqualTo(IJob.STATUS_DONE);
        verifyNoMoreInteractions(mMockBusListener);
    }

    @Test
    public void testMultiSuccessfulJobsOnUIThread() {
        final List<IJob> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final IJob job = mock(IJob.class);
            jobs.add(job);
            mManager.addJob(job);
        }
        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener, times(10)).onEventMainThread(any(IJobQueueEvent.class));
        verifyNoMoreInteractions(mMockBusListener);
    }

    @After
    public void tearDown() {
        EventBus.getDefault().unregister(mMockBusListener);
    }

}
