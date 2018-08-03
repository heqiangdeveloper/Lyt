package com.cimcitech.lyt.task.message;

import android.content.Context;

import com.cimcitech.lyt.sql.MsgSqlDao;
import com.cimcitech.lyt.task.BaseAsyncTask;
import com.cimcitech.lyt.task.OnTaskFinishedListener;

public class UpdateMessageTask extends BaseAsyncTask<Boolean> {
    private Context context;
    private int id;
    private int opened;

    public UpdateMessageTask(Context context, OnTaskFinishedListener<Boolean>
            onTaskFinishedListener, int id,int opened) {
        super(context, onTaskFinishedListener);
        this.context = context;
        this.id = id;
        this.opened = opened;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        MsgSqlDao dao = MsgSqlDao.getInstance(context);
        return dao.update(id,opened);
    }
}
