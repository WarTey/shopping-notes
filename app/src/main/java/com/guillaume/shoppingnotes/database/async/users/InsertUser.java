package com.guillaume.shoppingnotes.database.async.users;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.async.interfaces.UsersInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class InsertUser extends AsyncTask<UsersInterface, Void, Void> {

    private UsersInterface mListener;
    private AppDatabase db;
    private User user;

    public InsertUser(AppDatabase db, User user) {
        this.db = db;
        this.user = user;
    }

    @Override
    protected Void doInBackground(UsersInterface... registerUserInterfaces) {
        mListener = registerUserInterfaces[0];
        db.userDao().insertUser(user);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) { mListener.userCreated(); }
}
