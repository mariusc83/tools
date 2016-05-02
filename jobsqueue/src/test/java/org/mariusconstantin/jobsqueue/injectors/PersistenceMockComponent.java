package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.injectors.scopes.Persistence;

import dagger.Component;

@Persistence
@Component(modules = {PersistenceInjectMockModule.class})
public interface PersistenceMockComponent extends IPersistenceInjectComponent {

}