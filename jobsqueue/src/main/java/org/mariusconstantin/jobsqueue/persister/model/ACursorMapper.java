package org.mariusconstantin.jobsqueue.persister.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 4/7/2016.
 */
public abstract class ACursorMapper<T> {

    private Cursor mCursor;


    protected abstract void bindColumns(@NonNull Cursor cursor);

    @NonNull
    protected abstract T bind(@NonNull Cursor cursor);

    public T convert(@NonNull Cursor cursor) {
        if (cursor != mCursor) {
            mCursor = cursor;
            bindColumns(mCursor);
        }

        return bind(cursor);
    }
}
