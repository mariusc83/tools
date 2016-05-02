package org.mariusconstantin.jobsqueue.persister.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 4/7/2016.
 */
public class CategoryMapper extends ACursorMapper<Category> {

    @Override
    protected void bindColumns(@NonNull Cursor cursor) {
        
    }

    @NonNull
    @Override
    protected Category bind(@NonNull Cursor cursor) {
        return null;
    }
}
