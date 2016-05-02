package org.mariusconstantin.jobsqueue.injectors;

import android.content.Context;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.injectors.qualifiers.ApplicationContext;
import org.mariusconstantin.jobsqueue.injectors.scopes.Persistence;
import org.mariusconstantin.jobsqueue.logs.ILogger;
import org.mariusconstantin.jobsqueue.persister.DatabaseHelper;
import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;

import dagger.Module;
import dagger.Provides;

@Persistence
@Module
public class PersistenceInjectMockModule {

    @NonNull
    @ApplicationContext
    private final Context mMockApplicationContext;

    private ILogger mMockLogger;

    private DatabaseHelper mMockDatabaseHelper;

    private CategoryDAO mMockCategoryDao;


    public PersistenceInjectMockModule(@NonNull @ApplicationContext Context mockApplicationContext) {
        this.mMockApplicationContext = mockApplicationContext;
    }

    public PersistenceInjectMockModule setMockCategoryDao(CategoryDAO mockCategoryDao) {
        mMockCategoryDao = mockCategoryDao;
        return this;
    }

    public PersistenceInjectMockModule setMockDatabaseHelper(DatabaseHelper mockDatabaseHelper) {
        mMockDatabaseHelper = mockDatabaseHelper;
        return this;
    }

    public PersistenceInjectMockModule setMockLogger(ILogger mockLogger) {
        mMockLogger = mockLogger;
        return this;
    }

    @Provides
    @Persistence
    public ILogger provideLogger() {
        return mMockLogger;
    }

    @Provides
    @Persistence
    public DatabaseHelper provideDatabaseHelper(ILogger logger) {
        return mMockDatabaseHelper;
    }


    @Provides
    @Persistence
    public CategoryDAO provideCategoryDao(DatabaseHelper databaseHelper) {
        return mMockCategoryDao;
    }
}