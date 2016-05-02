package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.injectors.scopes.Jobqueue;

import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@Jobqueue
@Component(modules = {JobsInjectModule.class})
public interface JobsComponent extends IJobsInjectComponent {
}
