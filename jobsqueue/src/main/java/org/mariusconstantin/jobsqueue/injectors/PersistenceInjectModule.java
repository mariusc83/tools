package org.mariusconstantin.jobsqueue.injectors;

import android.content.Context;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.injectors.qualifiers.ApplicationContext;
import org.mariusconstantin.jobsqueue.injectors.scopes.Persistence;
import org.mariusconstantin.jobsqueue.logs.ILogger;
import org.mariusconstantin.jobsqueue.logs.LoggerBuilder;
import org.mariusconstantin.jobsqueue.persister.DatabaseHelper;
import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MConstantin on 4/7/2016.
 */
@Persistence
@Module
public class PersistenceInjectModule {
    @NonNull
    @ApplicationContext
    private final Context mApplicationContext;

    public PersistenceInjectModule(@NonNull @ApplicationContext Context mApplicationContext) {
        this.mApplicationContext = mApplicationContext;
    }


    @Provides
    @Persistence
    public ILogger provideLogger() {
        return new LoggerBuilder(BuildConfig.DEBUG).build();
    }

    @Provides
    @Persistence
    public DatabaseHelper provideDatabaseHelper(ILogger logger) {
        return new DatabaseHelper(mApplicationContext, logger);
    }


    @Provides
    @Persistence
    public CategoryDAO provideCategoryDao(DatabaseHelper databaseHelper) {
        return new CategoryDAO(databaseHelper);
    }
}
