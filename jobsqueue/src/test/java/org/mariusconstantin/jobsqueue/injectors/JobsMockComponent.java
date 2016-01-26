package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.manager.JobsManagerTest;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@Singleton
@Component(modules = {JobsManagerTest.InjectMockModule.class})
public interface JobsMockComponent extends IInjectComponent {
}
