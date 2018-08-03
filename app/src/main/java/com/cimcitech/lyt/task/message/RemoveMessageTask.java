package com.cimcitech.lyt.task.message;

import android.content.Context;

import com.cimcitech.lyt.sql.MsgSqlDao;
import com.cimcitech.lyt.task.BaseAsyncTask;
import com.cimcitech.lyt.task.OnTaskFinishedListener;

public class RemoveMessageTask extends BaseAsyncTask<Boolean> {
    private Context context;
    private int id;

    public RemoveMessageTask(Context context, OnTaskFinishedListener<Boolean>
            onTaskFinishedListener, int id) {
        super(context, onTaskFinishedListener);
        this.context = context;
        this.id = id;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        MsgSqlDao dao = MsgSqlDao.getInstance(context);
        return dao.delete(id);
    }
}
