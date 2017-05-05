package com.android.launcher4.util;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.launcher4.LauncherProvider;

/**
 * An extension of LauncherProvider backed up by in-memory database.
 */
public class TestLauncherProvider extends LauncherProvider {

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    protected synchronized void createDbIfNotExists() {
        if (mOpenHelper == null) {
            mOpenHelper = new MyDatabaseHelper(getContext());
        }
    }

    public SQLiteOpenHelper getHelper() {
        createDbIfNotExists();
        return mOpenHelper;
    }

    @Override
    protected void notifyListeners() { }

    private static class MyDatabaseHelper extends DatabaseHelper {
        public MyDatabaseHelper(Context context) {
            super(context, null, null);
            initIds();
        }

        @Override
        public long getDefaultUserSerial() {
            return 0;
        }

        @Override
        protected void onEmptyDbCreated() { }
    }
}