package org.mariusconstantin.jobsqueue.injectors;

import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@ForJobsQueue
@Component(modules = {InjectModule.class})
public interface JobsComponent extends IInjectComponent {
}
