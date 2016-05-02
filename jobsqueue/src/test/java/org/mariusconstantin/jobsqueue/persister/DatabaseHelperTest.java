package org.mariusconstantin.jobsqueue.persister;

/**
 * Created by MConstantin on 3/6/2016.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.logs.ILogger;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Marius on 1/26/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class DatabaseHelperTest {

    @Mock
    ILogger mMockLogger;

    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setUp() {
        mDatabaseHelper = new DatabaseHelper(RuntimeEnvironment.application, mMockLogger);
    }

    @Test
    public void testDbCreate() {
        assertThat(mDatabaseHelper.getWritableDatabase()).isNotNull();
    }

    @After
    public void tearDown() {
        DatabaseHelper.dropDatabase(mDatabaseHelper.getWritableDatabase());
    }
}
