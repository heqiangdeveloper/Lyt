package com.cimcitech.lyt.task.message;

import android.content.Context;

import com.cimcitech.lyt.sql.MsgSqlDao;
import com.cimcitech.lyt.task.BaseAsyncTask;
import com.cimcitech.lyt.task.OnTaskFinishedListener;

public class QueryUnReadMessageTask extends BaseAsyncTask<Integer> {
    private Context context;

    public QueryUnReadMessageTask(Context context, OnTaskFinishedListener<Integer>
            onTaskFinishedListener) {
        super(context, onTaskFinishedListener);
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        MsgSqlDao dao = MsgSqlDao.getInstance(context);
        return dao.queryUnReadMsg();
    }
}
