package org.mariusconstantin.jobsqueue.persister;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import org.mariusconstantin.jobsqueue.injectors.qualifiers.ApplicationContext;
import org.mariusconstantin.jobsqueue.logs.ILogger;
import org.mariusconstantin.jobsqueue.persister.dao.ADao;
import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;
import org.mariusconstantin.jobsqueue.persister.model.Category;
import org.mariusconstantin.jobsqueue.persister.model.IDbEntry;
import org.mariusconstantin.jobsqueue.persister.table.CategoryTableBuilder;
import org.mariusconstantin.jobsqueue.persister.table.ITableBuilder;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by MConstantin on 3/4/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final Map<Class<?>, ITableBuilder> mTableBuilder = new HashMap<>();

    static {
        mTableBuilder.put(CategoryDAO.class, new CategoryTableBuilder());
    }

    private static final String DB_NAME = "jobs_db";
    private static final int DB_VERSION = 1;

    private final ILogger mLogger;

    @Inject
    public DatabaseHelper(@NonNull @ApplicationContext Context context, ILogger logger) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mLogger = logger;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createDbTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /* package */
    private static void createDbTables(@NonNull SQLiteDatabase database) {
        for (ITableBuilder builder : getBuilders()) {
            builder.buildTable(database);
        }
    }

    /* package */
    static void clearDatabase(@NonNull SQLiteDatabase db) throws SQLException {
        for (ITableBuilder builder : getBuilders()) {
            builder.clear(db);
        }
    }

    /* package */
    static void dropDatabase(@NonNull SQLiteDatabase db) throws SQLException {
        for (ITableBuilder builder : getBuilders()) {
            builder.drop(db);
        }
    }

    @NonNull
    private static ITableBuilder[] getBuilders() {
        return new ITableBuilder[]{makeBuilder(CategoryDAO.class)};
    }

    public static <DaoClass extends ADao<?>> ITableBuilder makeBuilder(@NonNull Class<DaoClass> daoClass) {
        return mTableBuilder.get(daoClass);
    }
}
