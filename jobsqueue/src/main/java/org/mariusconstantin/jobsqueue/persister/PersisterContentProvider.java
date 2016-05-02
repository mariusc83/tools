package org.mariusconstantin.jobsqueue.persister;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import org.mariusconstantin.jobsqueue.injectors.DaggerPersistenceComponent;
import org.mariusconstantin.jobsqueue.injectors.IPersistenceInjectComponent;
import org.mariusconstantin.jobsqueue.injectors.PersistenceInjectModule;
import org.mariusconstantin.jobsqueue.injectors.scopes.Persistence;
import org.mariusconstantin.jobsqueue.logs.ILogger;
import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;
import org.mariusconstantin.jobsqueue.persister.dao.ICategoryContract;

import javax.inject.Inject;

import static org.mariusconstantin.jobsqueue.persister.UriUtils.buildUriFromId;

/**
 * Created by MConstantin on 4/7/2016.
 */
public class PersisterContentProvider extends ContentProvider {

    private static final String TAG = PersisterContentProvider.class.getSimpleName();

    @Persistence
    @Inject
    CategoryDAO mCategoryDAO;
    IPersistenceInjectComponent mComponent;

    @Persistence
    @Inject
    ILogger mLogger;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int MATCH_CATEGORIES = 1;
    private static final int MATCH_CATEGORY_ID = 2;

    static {
        mUriMatcher.addURI(IPersisterContract.AUTHORITY, ICategoryContract.Paths.PATH_CATEGORIES, MATCH_CATEGORIES);
        mUriMatcher.addURI(IPersisterContract.AUTHORITY, ICategoryContract.Paths.PATH_CATEGORIES + "/#", MATCH_CATEGORY_ID);
    }

    @Override
    public boolean onCreate() {
        mComponent = getInjectionComponent();
        mComponent.inject(this);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final int match = mUriMatcher.match(uri);
        Uri toReturn = null;
        long rowId = 0;

        if (values == null)
            return null;

        switch (match) {
            case MATCH_CATEGORIES:
                try {
                    rowId = mCategoryDAO.insert(values);
                    toReturn = ICategoryContract.CATEGORIES_URI;
                } catch (DbPersistException e) {
                    mLogger.logE(TAG, e.getMessage(), e);
                }
                break;
            default:
                throw new IllegalArgumentException("The provided uri: [ " + uri.toString() + " ] is not supported");
        }

        if (rowId > 0 && toReturn != null) {
            notifyChange(toReturn);
            return buildUriFromId(toReturn, String.valueOf(rowId));
        }

        return toReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = mUriMatcher.match(uri);
        int updatedRows = 0;
        switch (match) {
            case MATCH_CATEGORY_ID:
                try {
                    updatedRows=mCategoryDAO.
                } catch (DbPersistException e) {
                    mLogger.logE(TAG, e.getMessage(), e);
                }
                break;
            default:
                throw new IllegalArgumentException("The provided uri: [ " + uri.toString() + " ] is not supported");
        }
        return 0;
    }

    private void notifyChange(@NonNull Uri uri) {
        notifyChange(uri, null, null);
    }

    private void notifyChange(@NonNull Uri uri, @Nullable String id) {
        notifyChange(uri, id, null);
    }

    private void notifyChange(@NonNull Uri uri, @Nullable String id, @Nullable ContentObserver contentObserver) {
        //noinspection ConstantConditions
        getContext().getContentResolver().notifyChange(id != null ? UriUtils.buildUriFromId(uri, id) : uri, contentObserver);
    }

    /* package */
    @NonNull
    @SuppressWarnings("ConstantConditions")
    @VisibleForTesting
    IPersistenceInjectComponent getInjectionComponent() {
        return DaggerPersistenceComponent
                .builder()
                .persistenceInjectModule(new PersistenceInjectModule(getContext().getApplicationContext()))
                .build();
    }
}
