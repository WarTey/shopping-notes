package com.guillaume.shoppingnotes.database.async.items;

import android.os.AsyncTask;
import android.util.Log;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.ItemsInterface;
import com.guillaume.shoppingnotes.model.Item;

public class InsertItems extends AsyncTask<Void, Void, Void> {

    private ItemsInterface mListener;
    private String userEmail;
    private AppDatabase db;
    private Item item;

    public InsertItems(ItemsInterface itemsInterface, String userEmail, AppDatabase db, Item item) {
        mListener = itemsInterface;
        this.userEmail = userEmail;
        this.db = db;
        this.item = item;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("debug", "coucou");
        if (db.userDao().getUserByEmail(userEmail) != null) {
            if (db.itemDao().getItemById(item.getId()) == null)
                db.itemDao().insertItem(item);
            else
                db.itemDao().updateItem(item);
            mListener.itemCreated(item);
        }
        return null;
    }
}
