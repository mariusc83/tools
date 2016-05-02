package org.mariusconstantin.jobsqueue.persister.model;

import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 3/6/2016.
 */
public interface IDbEntry {

    long getId();

    String getName();

    void setName(String name);
}
