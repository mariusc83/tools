package org.mariusconstantin.jobsqueue.persister.dao;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.DatabaseHelper;
import org.mariusconstantin.jobsqueue.persister.model.Category;
import org.mariusconstantin.jobsqueue.persister.table.CategoryTableBuilder;

import javax.inject.Inject;

/**
 * Created by MConstantin on 3/6/2016.
 */
public class CategoryDAO extends ADao<Category> {

    @Inject
    public CategoryDAO(@NonNull DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }

    @Override
    protected Category buildEntity(@NonNull Cursor cursor) {
        return null;
    }


}
