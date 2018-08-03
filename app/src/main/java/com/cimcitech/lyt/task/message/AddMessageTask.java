package com.cimcitech.lyt.task.message;

import android.content.Context;

import com.cimcitech.lyt.bean.message.MessageData;
import com.cimcitech.lyt.sql.MsgSqlDao;
import com.cimcitech.lyt.task.BaseAsyncTask;
import com.cimcitech.lyt.task.OnTaskFinishedListener;

/**
 * Created by Jimmy on 2016/10/11 0011.
 */
public class AddMessageTask extends BaseAsyncTask<Boolean> {
    private Context context;
    private MessageData mMessageData;

    public AddMessageTask(Context context, OnTaskFinishedListener<Boolean>
            onTaskFinishedListener, MessageData messageData) {
        super(context, onTaskFinishedListener);
        this.context = context;
        mMessageData = messageData;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (mMessageData != null) {
            MsgSqlDao dao = MsgSqlDao.getInstance(context);
            return dao.add(mMessageData);
        } else {
            return false;
        }
    }
}
