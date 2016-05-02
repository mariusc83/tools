package org.mariusconstantin.jobsqueue.persister.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.mariusconstantin.jobsqueue.persister.DatabaseHelper;
import org.mariusconstantin.jobsqueue.persister.DbPersistException;
import org.mariusconstantin.jobsqueue.persister.model.IDbEntry;
import org.mariusconstantin.jobsqueue.persister.table.ITableBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static org.mariusconstantin.jobsqueue.persister.DatabaseHelper.makeBuilder;

/**
 * Created by MConstantin on 3/6/2016.
 */
public abstract class ADao<T extends IDbEntry> {
    public static final int EMPTY_ID = -1;

    @NonNull
    private final DatabaseHelper mDatabaseHelper;

    @NonNull
    private final ITableBuilder mTableBuilder;

    protected ADao(@NonNull DatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
        this.mTableBuilder = makeBuilder(this.getClass());
    }

    /**
     * @param contentValues
     * @return the inserted row id or {@link #EMPTY_ID} if the operation was not successful
     * @throws DbPersistException
     */
    public long insert(@NonNull ContentValues contentValues) throws DbPersistException {
        try {
            return mDatabaseHelper.getWritableDatabase().insert(mTableBuilder.getTableName(), null, contentValues);
        } catch (SQLException e) {
            throw new DbPersistException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @param contentValues
     * @return the updated rows number or {@code 0} if no row was updated
     * @throws DbPersistException
     */
    public int update(@NonNull ContentValues contentValues) throws DbPersistException{

    }


    /**
     * @return all {@link T} entities from this table
     * @throws DbPersistException
     */
    public List<T> fetch() throws DbPersistException {
        Cursor cursor = null;
        try {
            final ArrayList<T> toReturn = new ArrayList<>();
            cursor = mDatabaseHelper.getReadableDatabase().query(null, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    toReturn.add(buildEntity(cursor));
                } while (cursor.moveToNext());
            }

            return toReturn;
        } catch (SQLException e) {
            throw new DbPersistException(e.getMessage(), e);
        } finally {
            closeCursor(cursor);
        }
    }

    protected abstract T buildEntity(@NonNull Cursor cursor);

    static void closeCursor(@Nullable Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }

    @NonNull
    public ITableBuilder getTableBuilder() {
        return mTableBuilder;
    }
}
