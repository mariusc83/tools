package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.manager.JobsManager;

/**
 * Created by Marius on 1/26/2016.
 */
public interface IInjectComponent {
    void inject(JobsManager manager);
}
