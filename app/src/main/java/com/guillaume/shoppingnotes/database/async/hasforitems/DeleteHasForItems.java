package com.guillaume.shoppingnotes.database.async.hasforitems;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.HasForItem;

public class DeleteHasForItems extends AsyncTask<Void, Void, Void> {

    private String userEmail;
    private HasForItem hasForItem;
    private AppDatabase db;

    public DeleteHasForItems(AppDatabase db, String userEmail, HasForItem hasForItem) {
        this.db = db;
        this.hasForItem = hasForItem;
        this.userEmail = userEmail;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null)
            db.hasForItemDao().deleteHasForItem(hasForItem);
        return null;
    }
}
