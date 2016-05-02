package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.manager.JobsManagerAsyncTest;

import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@Jobqueue
@Component(modules = {JobsManagerAsyncTest.InjectMockModule.class})
public interface AsyncJobsMockComponent extends IJobsInjectComponent {
}
