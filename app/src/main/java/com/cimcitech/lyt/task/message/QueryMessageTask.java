package com.cimcitech.lyt.task.message;

import android.content.Context;

import com.cimcitech.lyt.bean.message.MessageData;
import com.cimcitech.lyt.sql.MsgSqlDao;
import com.cimcitech.lyt.task.BaseAsyncTask;
import com.cimcitech.lyt.task.OnTaskFinishedListener;

import java.util.List;

/**
 * Created by Jimmy on 2016/10/11 0011.
 */
public class QueryMessageTask extends BaseAsyncTask<List<MessageData>> {
    private Context context;

    public QueryMessageTask(Context context, OnTaskFinishedListener<List<MessageData>> onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        this.context = context;
    }

    @Override
    protected List<MessageData> doInBackground(Void... params) {
        MsgSqlDao dao = MsgSqlDao.getInstance(context);
        return dao.query();
    }
}
