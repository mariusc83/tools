package org.mariusconstantin.jobsqueue.injectors;

import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@ForJobsQueue
@Component(modules = {InjectMockModule.class})
public interface JobsMockComponent extends IInjectComponent {
}
