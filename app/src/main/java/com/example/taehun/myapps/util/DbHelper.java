package com.example.taehun.myapps.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taehun on 15. 7. 15..
 */
public class DbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "movieDB.db";
    static final int Version_DB = 1;

    static final String generTable = "CREATE TABLE movieGenre ( _id integer primary key autoincrement," +
            "gerneNum integer ," +
            "gerneString text ); ";
    static final String favoriteTable= "CREATE TABLE FAVOR ( " +
            "_id integer primary key autoincrement, " +
            "title text, " +
            "genre_ids text, " +
            "movie_id text not null unique, " +//id
            "overview text, " +
            "release_date text, " +
            "poster_path text, " +
            "popularity text, " +
            "vote_average text, " +
            "vote_count text, " +
            "favor numeric );";


    static final String traillerTable  = "CREATE TABLE TRAILER ( _id integer primary key autoincrement, " +
            "movie_id text, " +
            "url_add text, " +
            "movie_title text);";

    static final String reviewsTable = "CREATE TABLE REVIEWS ( _id integer primary key autoincrement, " +
            "movie_id text, " +
            "auth text, " +
            "content text );";
    public DbHelper(Context context) {
        super(context, DB_NAME, null, Version_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(generTable);
        db.execSQL(favoriteTable);
        db.execSQL(traillerTable);
        db.execSQL(reviewsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists student";
        db.execSQL(sql);

        onCreate(db);
    }
}
