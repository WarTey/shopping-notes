package com.guillaume.shoppingnotes.database.async;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.async.interfaces.LoginUserInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class LoginUser extends AsyncTask<LoginUserInterface, Void, User> {

    private LoginUserInterface mListener;
    private AppDatabase db;
    private String email;

    public LoginUser(AppDatabase db, String email) {
        this.email = email;
        this.db = db;
    }

    @Override
    protected User doInBackground(LoginUserInterface... loginUserInterfaces) {
        mListener = loginUserInterfaces[0];
        return db.userDao().getUserByEmail(email);
    }

    @Override
    protected void onPostExecute(User user) { mListener.userLogin(user); }
}
