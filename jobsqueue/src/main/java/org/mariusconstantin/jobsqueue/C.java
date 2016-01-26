package org.mariusconstantin.jobsqueue;

import java.util.concurrent.TimeUnit;

/**
 * Created by MConstantin on 1/27/2016.
 */
public class C {

    public static final int NUMBER_OF_THREADS=Runtime.getRuntime().availableProcessors();
    public static final long KEEP_ALIVE_TIME= TimeUnit.SECONDS.toMillis(3);
}
