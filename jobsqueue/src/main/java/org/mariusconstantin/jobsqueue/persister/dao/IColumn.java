package org.mariusconstantin.jobsqueue.persister.dao;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by MConstantin on 3/6/2016.
 */
public interface IColumn extends BaseColumns {


    interface Type {
        String NONE = "none";
        String TEXT = "text";
        String INTEGER = "integer";
        String REAL = "real";
    }

    @StringDef(value = {Type.NONE, Type.TEXT, Type.INTEGER, Type.REAL})
    @Retention(RetentionPolicy.SOURCE)
    @interface ColumnType {}

    @ColumnType @NonNull String  getType();

    boolean isPrimary();

    boolean hasAutoIncrement();

    String getName();

}
