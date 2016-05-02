package org.mariusconstantin.jobsqueue.persister.table;

import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.persister.dao.Column;
import org.mariusconstantin.jobsqueue.persister.dao.ICategoryContract;
import org.mariusconstantin.jobsqueue.persister.dao.IColumn;

/**
 * Created by MConstantin on 3/6/2016.
 */
public class CategoryTableBuilder extends ATableBuilder {

    @NonNull
    @Override
    public IColumn[] getColumns() {
        final IColumn idColumn = new Column.Builder(IColumn.Type.INTEGER, ICategoryContract.ColumnNames._ID)
                .isIsAutoIncrement(true)
                .isPrimary(true)
                .build();
        final IColumn nameColumn = new Column.Builder(IColumn.Type.TEXT, ICategoryContract.ColumnNames.NAME)
                .build();
        final IColumn priorityColumn = new Column.Builder(IColumn.Type.INTEGER, ICategoryContract.ColumnNames.PRIORITY)
                .build();
        return new IColumn[]{idColumn, nameColumn, priorityColumn};
    }

    @NonNull
    @Override
    public String getTableName() {
        return ICategoryContract.TABLE_NAME;
    }


}
