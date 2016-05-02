package org.mariusconstantin.jobsqueue.logs;

import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 3/4/2016.
 */
public interface ILogger {

    void logV(@NonNull String tag, @NonNull String message);

    void logD(@NonNull String tag, @NonNull String message);

    void logE(@NonNull String tag, @NonNull String message);

    void logE(@NonNull String tag, @NonNull String message, @NonNull Throwable e);
}
