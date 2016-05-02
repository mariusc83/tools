package org.mariusconstantin.jobsqueue.logs;

import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 3/4/2016.
 */
public abstract class ALogger implements ILogger {

    @Override
    public void logV(@NonNull String tag, @NonNull String message) {
    }

    @Override
    public void logD(@NonNull String tag, @NonNull String message) {

    }

    @Override
    public void logE(@NonNull String tag, @NonNull String message) {

    }

    @Override
    public void logE(@NonNull String tag, @NonNull String message, @NonNull Throwable e) {

    }
}
