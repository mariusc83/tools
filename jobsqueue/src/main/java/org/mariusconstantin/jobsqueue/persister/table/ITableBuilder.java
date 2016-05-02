package org.mariusconstantin.jobsqueue.persister.table;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.dao.IColumn;

/**
 * Created by MConstantin on 3/6/2016.
 */
public interface ITableBuilder {
    @NonNull
    IColumn[] getColumns();

    @NonNull
    String getTableName();

    void buildTable(@NonNull SQLiteDatabase database) throws SQLException;

    void clear(@NonNull SQLiteDatabase db) throws SQLException;

    void drop(@NonNull SQLiteDatabase db) throws SQLException;
}