package com.guillaume.shoppingnotes.database.async.lists;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.List;

public class DeleteList extends AsyncTask<Void, Void, Void> {

    private String userEmail;
    private AppDatabase db;
    private List list;

    public DeleteList(AppDatabase db, List list, String userEmail) {
        this.db = db;
        this.list = list;
        this.userEmail = userEmail;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null)
            db.listDao().deleteList(list);
        return null;
    }
}
