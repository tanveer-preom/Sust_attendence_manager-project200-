package com.example.tanveer.sustattendancemanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tanveer on 8/17/15.
 */
public class SqlDb {
    private SQLiteDatabase db;

    public class FeedReaderDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FeedReader.db";

        public FeedReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table data_update(class_id integer,course_id Text,day integer,month integer,year integer,reg_no Text,late integer,success integer,done integer,late_min integer,year_id Text,semester_id Text)");
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL("drop table data_update if exists");
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
    public void open(Context app)
    {
        FeedReaderDbHelper helper =new FeedReaderDbHelper(app);
        db=helper.getWritableDatabase();

    }
    public void close()
    {
        db.close();
    }
    public void execupdate(String sql)
    {
       db.execSQL(sql);
        Log.i("tanvy",sql);
    }
    public Cursor execquery(String sql)
    {
        return db.rawQuery(sql,null);
    }

}
