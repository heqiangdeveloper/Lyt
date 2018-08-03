package com.cimcitech.lyt.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cimcitech.lyt.bean.message.MessageContent;
import com.cimcitech.lyt.bean.message.MessageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianghe on 2018/7/5.
 */

public class MsgSqlDao {
    private MsgSQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public MsgSqlDao(Context context) {
        helper = new MsgSQLiteOpenHelper(context);
    }

    public static MsgSqlDao getInstance(Context context) {
        return new MsgSqlDao(context);
    }

    //新增
    public boolean add(MessageData msgData) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MsgSQLiteOpenHelper.TIME, msgData.getTime());
        values.put(MsgSQLiteOpenHelper.TITLE, msgData.getTitle());
        values.put(MsgSQLiteOpenHelper.AREA, msgData.getMessageContent().getWhere());
        values.put(MsgSQLiteOpenHelper.STARTTIME, msgData.getMessageContent().getStartTime());
        values.put(MsgSQLiteOpenHelper.ENDTIME, msgData.getMessageContent().getEndTime());
        values.put(MsgSQLiteOpenHelper.CONTENTDESC, msgData.getMessageContent().getContentDesc());
        values.put(MsgSQLiteOpenHelper.OPENED, 0);//默认是未读
        long row = db.insert(MsgSQLiteOpenHelper.TABLE_NAME, null, values);
        db.close();
        return row != -1;
    }

    //查找
    public List<MessageData> query() {
        List<MessageData> messageDatas = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(MsgSQLiteOpenHelper.TABLE_NAME, null,
                null,null, null, null, null, null);
        MessageData messageData;
        MessageContent messageContent;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MsgSQLiteOpenHelper.ID));
            String title = cursor.getString(cursor.getColumnIndex(MsgSQLiteOpenHelper.TITLE));
            String time = cursor.getString(cursor.getColumnIndex(MsgSQLiteOpenHelper.TIME));
            String where = cursor.getString(cursor.getColumnIndex(MsgSQLiteOpenHelper.AREA));
            String starttime= cursor.getString(cursor.getColumnIndex(MsgSQLiteOpenHelper.STARTTIME));
            String endtime = cursor.getString(cursor.getColumnIndex(MsgSQLiteOpenHelper.ENDTIME));
            String contentdesc = cursor.getString(cursor.getColumnIndex(MsgSQLiteOpenHelper.CONTENTDESC));
            int opened = cursor.getInt(cursor.getColumnIndex(MsgSQLiteOpenHelper.OPENED));
            messageContent = new MessageContent(contentdesc,where,starttime,endtime);
            messageData = new MessageData(opened,title,time,messageContent);
            messageData.setId(id);
            messageDatas.add(messageData);
        }
        cursor.close();
        db.close();
        helper.close();
        return messageDatas;
    }

    //查找
    public int queryUnReadMsg() {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(MsgSQLiteOpenHelper.TABLE_NAME, null,
                null,null, null, null, null, null);
        int num = 0;
        while (cursor.moveToNext()) {
            int opened = cursor.getInt(cursor.getColumnIndex(MsgSQLiteOpenHelper.OPENED));
           if(opened == 0){
               num++;
           }
        }
        cursor.close();
        db.close();
        helper.close();
        return num;
    }

    //删除
    public boolean delete(int id) {
        db = helper.getWritableDatabase();
        int row = db.delete(MsgSQLiteOpenHelper.TABLE_NAME, String.format("%s=?",
                MsgSQLiteOpenHelper.ID), new String[]{String.valueOf(id)});
        db.close();
        helper.close();
        return row != 0;
    }

    //更新
    public boolean update(int id, int opened){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MsgSQLiteOpenHelper.OPENED,opened);
        int row = db.update(MsgSQLiteOpenHelper.TABLE_NAME, values, String.format("%s=?",
                MsgSQLiteOpenHelper.ID), new String[]{String.valueOf(id)});
        db.close();
        helper.close();
        return row > 0;
    }

}
