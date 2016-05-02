package org.mariusconstantin.jobsqueue.persister.model;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.IPersisterContract;
import org.mariusconstantin.jobsqueue.persister.dao.ICategoryContract;

/**
 * Created by MConstantin on 3/6/2016.
 */
public class Category implements IDbEntry {

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull Category category) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(ICategoryContract.ColumnNames.NAME, category.getName());
        return contentValues;
    }
}
