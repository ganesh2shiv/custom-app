package com.data.app.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

final class DbCallback extends SupportSQLiteOpenHelper.Callback {

    private static final int VERSION = 1;

    DbCallback() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    }
}