package com.guillaume.shoppingnotes.database.async.users;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class UpdateUser extends AsyncTask<Void, Void, Void> {

    private String userEmail;
    private AppDatabase db;
    private User user;

    public UpdateUser(AppDatabase db, User user, String userEmail) {
        this.db = db;
        this.user = user;
        this.userEmail = userEmail;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (db.userDao().getUserByEmail(userEmail) != null)
            db.userDao().updateUser(user);
        return null;
    }
}
