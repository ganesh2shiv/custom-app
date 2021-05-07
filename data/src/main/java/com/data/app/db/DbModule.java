package com.data.app.db;

import android.content.Context;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;

import com.core.app.BuildConfig;
import com.squareup.sqlbrite3.BriteDatabase;
import com.squareup.sqlbrite3.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Module
@InstallIn(ApplicationComponent.class)
public final class DbModule {

    @Provides
    @Singleton
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder()
                .logger(msg -> Timber.tag("SqlBrite").d(msg))
                .build();
    }

    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase(SqlBrite sqlBrite, Context context) {
        SupportSQLiteOpenHelper.Configuration configuration = SupportSQLiteOpenHelper.Configuration.builder(context)
                .name("database.db")
                .callback(new DbCallback())
                .build();
        SupportSQLiteOpenHelper.Factory factory = new FrameworkSQLiteOpenHelperFactory();
        SupportSQLiteOpenHelper helper = factory.create(configuration);
        BriteDatabase database = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        database.setLoggingEnabled(BuildConfig.DEBUG);
        return database;
    }
}