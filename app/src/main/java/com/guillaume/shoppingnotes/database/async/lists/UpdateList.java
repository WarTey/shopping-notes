package com.guillaume.shoppingnotes.database.async.lists;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.List;

public class UpdateList extends AsyncTask<Void, Void, Void> {

    private String userEmail;
    private AppDatabase db;
    private List list;

    public UpdateList(AppDatabase db, List list, String userEmail) {
        this.db = db;
        this.list = list;
        this.userEmail = userEmail;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null)
            db.listDao().updateList(list);
        return null;
    }
}
