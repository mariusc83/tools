package org.mariusconstantin.jobsqueue.persister;

import android.content.Context;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;

import java.util.List;

/**
 * Created by MConstantin on 4/11/2016.
 */
public class CategoryDaoHelper implements ICategoryDaoHelper {

    private final Context mContext;

    public CategoryDaoHelper(Context context) {
        this.mContext = context;
    }


    @NonNull
    @Override
    public List<CategoryDAO> fetchAll() {
        return null;
    }

    @Override
    public long add(@NonNull CategoryDAO entity) {
        return 0;
    }

    @Override
    public int delete(@NonNull CategoryDAO categoryDAO) {
        return 0;
    }
}
