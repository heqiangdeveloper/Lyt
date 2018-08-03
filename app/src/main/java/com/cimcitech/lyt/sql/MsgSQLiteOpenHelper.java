package com.cimcitech.lyt.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qianghe on 2018/7/5.
 */

public class MsgSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String NAME = "msgsqlite.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "msg";

    public static final String ID = "id";
    public static final String TIME = "time";
    public static final String TITLE = "title";
    public static final String AREA = "area";
    public static final String STARTTIME = "starttime";
    public static final String ENDTIME = "endtime";
    public static final String CONTENTDESC = "contentdesc";
    public static final String OPENED = "opened";//是否读取，0未读，1已读

    public MsgSQLiteOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TIME + " VARCHAR(128), "
                + TITLE + " VARCHAR(128), "
                + AREA + " VARCHAR(48), "
                + STARTTIME + " VARCHAR(48), "
                + ENDTIME + " VARCHAR(48), "
                + CONTENTDESC + " VARCHAR(128), "
                + OPENED + " INTEGER "
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + TABLE_NAME;
        if (oldVersion != newVersion) {
            db.execSQL(sql);
            onCreate(db);
        }
    }
}
