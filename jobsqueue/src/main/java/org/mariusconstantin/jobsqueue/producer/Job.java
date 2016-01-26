package org.mariusconstantin.jobsqueue.producer;

import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 1/27/2016.
 */
public class Job implements IJob {

    @NonNull
    private final IExecutable mExecutable;

    private Job(@NonNull Builder builder) {
        this.mExecutable = builder.executable;
    }

    @Override
    public void doRun() throws Throwable {
        mExecutable.run();
    }

    public static class Builder {
        @NonNull
        private final IExecutable executable;

        public Builder(@NonNull IExecutable executable) {
            this.executable = executable;
        }

        public IJob build() {
            return new Job(this);
        }
    }
}
