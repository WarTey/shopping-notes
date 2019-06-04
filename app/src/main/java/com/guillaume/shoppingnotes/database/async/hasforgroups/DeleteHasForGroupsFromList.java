package com.guillaume.shoppingnotes.database.async.hasforgroups;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.List;

public class DeleteHasForGroupsFromList extends AsyncTask<Void, Void, Void> {

    private String userEmail;
    private AppDatabase db;
    private List list;

    public DeleteHasForGroupsFromList(String userEmail, List list, AppDatabase db) {
        this.userEmail = userEmail;
        this.list = list;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null)
            db.hasForGroupDao().deleteHasForGroups(list.getId());
        return null;
    }
}
