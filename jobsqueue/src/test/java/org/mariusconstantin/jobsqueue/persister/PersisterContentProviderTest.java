package org.mariusconstantin.jobsqueue.persister;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mariusconstantin.jobsqueue.BuildConfig;
import org.mariusconstantin.jobsqueue.injectors.DaggerPersistenceMockComponent;
import org.mariusconstantin.jobsqueue.injectors.IPersistenceInjectComponent;
import org.mariusconstantin.jobsqueue.injectors.PersistenceInjectMockModule;
import org.mariusconstantin.jobsqueue.injectors.PersistenceMockComponent;
import org.mariusconstantin.jobsqueue.logs.ILogger;
import org.mariusconstantin.jobsqueue.persister.dao.CategoryDAO;
import org.mariusconstantin.jobsqueue.persister.dao.ICategoryContract;
import org.mariusconstantin.jobsqueue.persister.model.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mariusconstantin.jobsqueue.persister.UriUtils.buildUriFromId;
import static org.mariusconstantin.jobsqueue.persister.UriUtils.idFromUri;
import static org.mariusconstantin.jobsqueue.utils.TestUtils.generateRandomString;


/**
 * Created by MConstantin on 4/8/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 16)
public class PersisterContentProviderTest {

    @Mock
    Context mMockContext;

    @Mock
    private ILogger mMockLogger;

    @Mock
    private DatabaseHelper mMockDatabaseHelper;

    @Mock
    private CategoryDAO mMockCategoryDao;

    @Mock
    private Category mMockCategory;

    private ShadowContentResolver mShadowContentResolver;
    private MockPersisterContentProvider mContentProvider;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        mShadowContentResolver = Shadows.shadowOf(contentResolver);
        final PersistenceInjectMockModule mockModule = new PersistenceInjectMockModule(mMockContext);
        mockModule
                .setMockCategoryDao(mMockCategoryDao)
                .setMockDatabaseHelper(mMockDatabaseHelper)
                .setMockLogger(mMockLogger);
        final PersistenceMockComponent mockComponent = DaggerPersistenceMockComponent
                .builder()
                .persistenceInjectMockModule(mockModule)
                .build();

        mContentProvider = new MockPersisterContentProvider(mockComponent);
        mContentProvider.onCreate();
        ShadowContentResolver.registerProvider(IPersisterContract.AUTHORITY, mContentProvider);
    }


    @Test
    public void should_return_a_valid_uri_when_insert_category_was_ok() throws Exception {
        // given
        final String categoryName = generateRandomString(10);
        final ContentValues contentValues = Category.toContentValues(mMockCategory);
        given(mMockCategory.getName()).willReturn(categoryName);
        given(mMockCategoryDao.insert(eq(contentValues))).willReturn(1L);

        // when
        final Uri uri = mShadowContentResolver.insert(ICategoryContract.CATEGORIES_URI, contentValues);

        // then
        assertThat(uri).isNotNull();
        verify(mMockCategoryDao).insert(eq(contentValues));
        verifyNoMoreInteractions(mMockCategoryDao);
        assertThat(uri).isEqualTo(buildUriFromId(ICategoryContract.CATEGORIES_URI, "1"));
        assertThat(idFromUri(uri)).isEqualTo("1");
    }

    @Test
    public void should_return_null_on_insert_db_exception() throws Exception{
        // given
        final String exceptionMessage="Exception test message";
        final String categoryName = generateRandomString(10);
        final ContentValues contentValues = Category.toContentValues(mMockCategory);
        given(mMockCategory.getName()).willReturn(categoryName);
        given(mMockCategoryDao.insert(eq(contentValues))).willThrow(new DbPersistException(exceptionMessage));

        // when
        final Uri uri = mShadowContentResolver.insert(ICategoryContract.CATEGORIES_URI, contentValues);

        // then
        assertThat(uri).isNull();
        verify(mMockCategoryDao).insert(eq(contentValues));
        verifyNoMoreInteractions(mMockCategoryDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_on_insert_if_bad_uri() throws Exception{
        // given
        final ContentValues contentValues = Category.toContentValues(mMockCategory);
        given(mMockCategoryDao.insert(eq(contentValues))).willReturn(1L);

        // when
        final Uri uri = IPersisterContract.BASE_CONTENT_URI.buildUpon().appendPath("test").build();
        mShadowContentResolver.insert(uri,contentValues);
    }




    public static final class MockPersisterContentProvider extends PersisterContentProvider {

        private final IPersistenceInjectComponent mInjectComponent;

        public MockPersisterContentProvider(IPersistenceInjectComponent injectComponent) {
            mInjectComponent = injectComponent;
        }


        @NonNull
        @Override
        IPersistenceInjectComponent getInjectionComponent() {
            return mInjectComponent;
        }
    }
}
