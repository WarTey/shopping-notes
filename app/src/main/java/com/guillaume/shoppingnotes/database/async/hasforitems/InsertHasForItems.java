package com.guillaume.shoppingnotes.database.async.hasforitems;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.HasForItem;

public class InsertHasForItems extends AsyncTask<Void, Void, Void> {

    private String userEmail,list_id, item_id;
    private AppDatabase db;

    public InsertHasForItems(String userEmail, String list_id, String item_id, AppDatabase db) {
        this.userEmail = userEmail;
        this.list_id = list_id;
        this.item_id = item_id;
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null && db.hasForItemDao().getHasForItemsById(item_id, list_id) == null)
            db.hasForItemDao().insertHasForItem(new HasForItem(false, list_id, item_id));
        return null;
    }
}
