package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;
import org.mariusconstantin.jobsqueue.manager.JobsManagerSyncTest;

import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@Jobqueue
@Component(modules = {JobsManagerSyncTest.InjectMockModule.class})
public interface SyncJobsMockComponent extends IJobsInjectComponent {
}
