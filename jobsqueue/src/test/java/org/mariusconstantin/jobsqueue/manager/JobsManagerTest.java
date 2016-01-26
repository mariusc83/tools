package org.mariusconstantin.jobsqueue.manager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.injectors.DaggerJobsMockComponent;
import org.mariusconstantin.jobsqueue.injectors.InjectMockModule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by Marius on 1/26/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class JobsManagerTest {

    private IJobsManager mManager;

    @Before
    public void setUp() {
        mManager = new JobsManager.Builder(DaggerJobsMockComponent.builder().injectMockModule(new InjectMockModule()).build())
                .build();
    }

    @Test
    public void testExecuteJob() {
        assertTrue(true);
    }

    @After
    public void tearDown() {
    }
}
