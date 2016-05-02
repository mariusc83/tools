package org.mariusconstantin.jobsqueue.logs;

/**
 * Created by MConstantin on 3/4/2016.
 */
public class LoggerBuilder {
    private final boolean mIsDebug;

    public LoggerBuilder(boolean isDebug) {
        mIsDebug = isDebug;
    }

    public ILogger build() {
        return mIsDebug ? new DebugLogger() : new DefaultLogger();
    }
}
