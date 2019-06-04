package com.guillaume.shoppingnotes.database.async.hasforgroups;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.HasForGroup;

public class InsertHasForGroups extends AsyncTask<Void, Void, Void> {

    private String userEmail;
    private AppDatabase db;
    private HasForGroup hasForGroup;

    public InsertHasForGroups(String userEmail, AppDatabase db, HasForGroup hasForGroup) {
        this.userEmail = userEmail;
        this.hasForGroup = hasForGroup;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null)
            db.hasForGroupDao().insertHasForGroup(hasForGroup);
        return null;
    }
}
