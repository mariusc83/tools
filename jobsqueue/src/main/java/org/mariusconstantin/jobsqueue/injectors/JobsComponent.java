package org.mariusconstantin.jobsqueue.injectors;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Marius on 1/26/2016.
 */

@Singleton
@Component(modules = {InjectModule.class})
public interface JobsComponent extends IInjectComponent {
}
