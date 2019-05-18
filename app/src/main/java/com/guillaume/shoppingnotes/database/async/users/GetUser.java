package com.guillaume.shoppingnotes.database.async.users;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.async.interfaces.UsersInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class GetUser extends AsyncTask<UsersInterface, Void, User> {

    private UsersInterface mListener;
    private AppDatabase db;
    private String email;

    public GetUser(AppDatabase db, String email) {
        this.email = email;
        this.db = db;
    }

    @Override
    protected User doInBackground(UsersInterface... loginUserInterfaces) {
        mListener = loginUserInterfaces[0];
        return db.userDao().getUserByEmail(email);
    }

    @Override
    protected void onPostExecute(User user) { mListener.userResponse(user); }
}
