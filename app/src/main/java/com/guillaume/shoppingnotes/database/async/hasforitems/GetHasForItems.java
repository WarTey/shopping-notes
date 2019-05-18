package com.guillaume.shoppingnotes.database.async.hasforitems;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.HasForItemsInterface;
import com.guillaume.shoppingnotes.model.HasForItem;

import java.util.List;

public class GetHasForItems extends AsyncTask<HasForItemsInterface, Void, List<HasForItem>> {

    private HasForItemsInterface mListener;
    private AppDatabase db;

    public GetHasForItems(AppDatabase db) { this.db = db; }

    @Override
    protected List<HasForItem> doInBackground(HasForItemsInterface... hasForItemsInterfaces) {
        mListener = hasForItemsInterfaces[0];
        return db.hasForItemDao().getHasForItems();
    }

    @Override
    protected void onPostExecute(List<HasForItem> hasForItems) { mListener.hasForItemsResponse(hasForItems); }
}
