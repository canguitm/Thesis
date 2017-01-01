package com.example.user.thesis;

import android.database.sqlite.SQLiteOpenHelper;

import com.clough.android.androiddbviewer.ADBVApplication;

/**
 * Created by user on 01/01/2017.
 */
public class CustomApplication extends ADBVApplication {

    @Override
    public SQLiteOpenHelper getDataBase() {
        return new DatabaseHelper(getApplicationContext());
    }

}