package com.example.taehun.myapps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taehun on 15. 7. 15..
 */
public class DbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "movieDB.db";
    static final int Version_DB = 1;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, Version_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE movieGenre ( _id integer primary key autoincrement," +
                "gerneNum integer ," +
                "gerneString text ); ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists student";
        db.execSQL(sql);

        onCreate(db);
    }
}
