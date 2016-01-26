package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.MainThread;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.bus.IJobQueueEvent;
import org.mariusconstantin.jobsqueue.consumer.IJobsConsumer;
import org.mariusconstantin.jobsqueue.consumer.JobsConsumer;
import org.mariusconstantin.jobsqueue.executors.UIThreadExecutor;
import org.mariusconstantin.jobsqueue.injectors.DaggerJobsMockComponent;
import org.mariusconstantin.jobsqueue.producer.IJob;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Marius on 1/26/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class JobsManagerTest {

    @Module
    @Singleton
    public class InjectMockModule {

        @Provides
        @Singleton
        public ExecutorService provideJobsExecutor() {
            return new UIThreadExecutor();
        }

        @Provides
        @Singleton
        public IJobsConsumer provideConsumer(ExecutorService executorService) {
            return new JobsConsumer(executorService);
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mManager = new JobsManager(DaggerJobsMockComponent.builder().injectMockModule(new InjectMockModule()).build());
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
    public void testMultiSuccessfulJobsOnUIThread() {
        final List<IJob> jobs=new ArrayList<>();
        for(int i=0;i<10;i++){
            final IJob job=mock(IJob.class);
            jobs.add(job);
            mManager.addJob(job);
        }
        // test if the EventBus event was triggered with the job complete status
        verify(mMockBusListener,times(10)).onEventMainThread(any(IJobQueueEvent.class));
        verifyNoMoreInteractions(mMockBusListener);
    }

    @After
    public void tearDown() {
        EventBus.getDefault().unregister(mMockBusListener);
    }

}
