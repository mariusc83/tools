package org.mariusconstantin.jobsqueue.injectors;

import org.mariusconstantin.jobsqueue.persister.PersisterContentProvider;

/**
 * Created by MConstantin on 4/7/2016.
 */
public interface IPersistenceInjectComponent {

    void inject(PersisterContentProvider contentProvider);
}
