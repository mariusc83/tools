package org.mariusconstantin.jobsqueue.persister.dao;

import android.net.Uri;
import android.provider.BaseColumns;

import org.mariusconstantin.jobsqueue.persister.IPersisterContract;

/**
 * Created by MConstantin on 3/6/2016.
 */
public interface ICategoryContract extends IPersisterContract {
    String TABLE_NAME = "category";

    interface ColumnNames extends BaseColumns {
        String NAME = "cat_name";
        String PRIORITY = "cat_priority";
    }

    interface Paths {
        String PATH_CATEGORIES = "categories";
    }

    Uri CATEGORIES_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(Paths.PATH_CATEGORIES).build();

}
