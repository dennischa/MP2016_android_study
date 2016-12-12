package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseOpen extends SQLiteOpenHelper {
// dskdshkdstahthdsalktkljakldststsatd
    public DataBaseOpen(Context context) {
        super(context, "db_table", null, 1);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE db_table"
                + "(id integer primary key autoincrement, "
                + "date TEXT, "
                + "time TEXT, "
                + "address TEXT, "
                + "latitude REAL, "
                + "longitude REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS db_table");
        onCreate(db);
    }
}