package org.mariusconstantin.jobsqueue.persister;

import android.support.annotation.NonNull;

import java.util.Stack;

/**
 * Created by MConstantin on 3/6/2016.
 */
public class DbPersistException extends RuntimeException {

    private Stack<Throwable> mStackedThrowables;

    private static final long serialVersionUID = 7034897190745766939L;

    public DbPersistException(String detailMessage) {
        super(detailMessage);
    }

    public DbPersistException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DbPersistException withThrowable(@NonNull Throwable t) {
        if (mStackedThrowables == null)
            mStackedThrowables = new Stack<>();
        mStackedThrowables.add(t);
        return this;
    }
}
