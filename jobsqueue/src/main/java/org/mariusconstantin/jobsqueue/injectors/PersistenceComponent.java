package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.injectors.scopes.Persistence;

import dagger.Component;

/**
 * Created by MConstantin on 4/7/2016.
 */

@Persistence
@Component(modules = {PersistenceInjectModule.class})
public interface PersistenceComponent extends IPersistenceInjectComponent {
}
