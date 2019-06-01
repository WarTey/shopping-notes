package com.guillaume.shoppingnotes.database.async.hasforitems;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.HasForItemsInterface;
import com.guillaume.shoppingnotes.model.List;

public class DeleteHasForItemsFromList extends AsyncTask<HasForItemsInterface, Void, Void> {

    private String userEmail;
    private AppDatabase db;
    private List list;

    public DeleteHasForItemsFromList(String userEmail, List list, AppDatabase db) {
        this.userEmail = userEmail;
        this.list = list;
        this.db = db;
    }

    @Override
    protected Void doInBackground(HasForItemsInterface... hasForItemsInterfaces) {
        HasForItemsInterface mListener = hasForItemsInterfaces[0];
        if (db.userDao().getUserByEmail(userEmail) != null) {
            db.hasForItemDao().deleteHasForItems(list.getId());
            mListener.hasForItemsDeleted(list);
        }
        return null;
    }
}
