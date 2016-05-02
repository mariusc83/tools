package org.mariusconstantin.jobsqueue.persister.dao;

import android.support.annotation.NonNull;

/**
 * Created by MConstantin on 3/6/2016.
 */
public class Column implements IColumn {

    @NonNull
    @ColumnType
    private final String mType;
    @NonNull
    private final String mName;

    private final boolean mIsPrimary;
    private final boolean mIsAutoIncrement;

    private Column(Builder builder) {
        this.mType = builder.mType;
        this.mName = builder.mName;
        mIsPrimary = builder.mIsPrimary;
        mIsAutoIncrement = builder.mIsAutoIncrement;
    }

    @NonNull
    @Override
    public String getType() {
        return mType;
    }

    @Override
    public boolean isPrimary() {
        return mIsPrimary;
    }

    @Override
    public boolean hasAutoIncrement() {
        return mIsAutoIncrement;
    }

    @Override
    public String getName() {
        return mName;
    }

    public static class Builder {
        private boolean mIsAutoIncrement;
        private boolean mIsPrimary;

        @ColumnType
        @NonNull
        private final String mType;

        @NonNull
        private final String mName;


        public Builder(@IColumn.ColumnType @NonNull String type, @NonNull String name) {
            this.mType = type;
            this.mName = name;
        }

        public Builder isIsAutoIncrement(boolean isAutoIncrement) {
            mIsAutoIncrement = isAutoIncrement;
            return this;
        }

        public Builder isPrimary(boolean isPrimary) {
            mIsPrimary = isPrimary;
            return this;
        }

        public IColumn build(){
            return new Column(this);
        }
    }
}