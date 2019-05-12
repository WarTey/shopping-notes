package com.guillaume.shoppingnotes.auth.async;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.auth.async.interfaces.LoginUserInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

import java.util.List;

public class LoginUser extends AsyncTask<LoginUserInterface, Void, List<User>> {

    private LoginUserInterface mListener;
    private AppDatabase db;

    public LoginUser(AppDatabase db) { this.db = db; }

    @Override
    protected List<User> doInBackground(LoginUserInterface... loginUserInterfaces) {
        mListener = loginUserInterfaces[0];
        return db.userDao().getUsers();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        mListener.userLogin(users);
    }
}
