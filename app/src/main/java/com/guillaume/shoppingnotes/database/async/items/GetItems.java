package com.guillaume.shoppingnotes.database.async.items;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.ItemsInterface;
import com.guillaume.shoppingnotes.model.Item;

import java.util.List;

public class GetItems extends AsyncTask<ItemsInterface, Void, List<Item>> {

    private ItemsInterface mListener;
    private AppDatabase db;

    public GetItems(AppDatabase db) { this.db = db; }

    @Override
    protected List<Item> doInBackground(ItemsInterface... itemsInterfaces) {
        mListener = itemsInterfaces[0];
        return db.itemDao().getItems();
    }

    @Override
    protected void onPostExecute(List<Item> items) { mListener.itemsResponse(items); }
}
