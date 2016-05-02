package org.mariusconstantin.jobsqueue.persister.table;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.dao.IColumn;

/**
 * Created by MConstantin on 3/6/2016.
 */
public abstract class ATableBuilder implements ITableBuilder {

    protected static final IColumn[] EMPTY_COLUMNS = new IColumn[]{};

    @NonNull
    @Override
    public abstract IColumn[] getColumns();

    @Override
    public void buildTable(@NonNull SQLiteDatabase database) throws SQLException {
        final StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS " + getTableName());
        sb.append("(");
        for (IColumn column : getColumns()) {
            sb.append(column.getName()).append(" ").append(column.getType());
            if (column.isPrimary()) {
                sb.append(" PRIMARY KEY ");
            }

            if (column.hasAutoIncrement()) {
                sb.append(" AUTOINCREMENT ");
            }
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(");");
        database.execSQL(sb.toString());
    }

    @Override
    public void clear(@NonNull SQLiteDatabase db) throws SQLException {
        db.delete(getTableName(), null, null);
    }

    @Override
    public void drop(@NonNull SQLiteDatabase db) throws SQLException {
        db.execSQL("DROP TABLE IF EXISTS " + getTableName());
    }

}
