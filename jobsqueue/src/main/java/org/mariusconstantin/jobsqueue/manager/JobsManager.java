package org.mariusconstantin.jobsqueue.manager;

import android.support.annotation.NonNull;
import org.mariusconstantin.jobsqueue.injectors.IInjectComponent;

/**
 * Created by Marius on 1/26/2016.
 */
public class JobsManager implements IJobsManager {

    private JobsManager(Builder builder) {
    }

    public static class Builder {
        @NonNull
        private final IInjectComponent mInjectComponent;

        public Builder(@NonNull IInjectComponent injectComponent) {
            this.mInjectComponent = injectComponent;
        }

        public IJobsManager build() {
            return new JobsManager(this);
        }
    }
}
