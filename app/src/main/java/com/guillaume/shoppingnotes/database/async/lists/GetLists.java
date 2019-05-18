package com.guillaume.shoppingnotes.database.async.lists;

import android.os.AsyncTask;
import android.util.Log;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.ListsInterface;
import com.guillaume.shoppingnotes.model.List;

public class GetLists extends AsyncTask<ListsInterface, Void, java.util.List<List>> {

    private ListsInterface mListener;
    private AppDatabase db;
    private String email;

    public GetLists(AppDatabase db, String email) {
        this.db = db;
        this.email = email;
    }

    @Override
    protected java.util.List<List> doInBackground(ListsInterface... getListsInterfaces) {
        mListener = getListsInterfaces[0];
        return db.listDao().getListsByUserId(db.userDao().getUserId(email), false);
    }

    @Override
    protected void onPostExecute(java.util.List<List> lists) { mListener.listsResponse(lists); }
}
