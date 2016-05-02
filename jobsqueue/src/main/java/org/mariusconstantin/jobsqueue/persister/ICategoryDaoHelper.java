package org.mariusconstantin.jobsqueue.persister;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;

import java.util.List;

/**
 * Created by MConstantin on 4/8/2016.
 */
public interface ICategoryDaoHelper {

    @NonNull
    List<CategoryDAO> fetchAll();

    long add(@NonNull CategoryDAO entity);

    int delete(@NonNull CategoryDAO categoryDAO);
}
