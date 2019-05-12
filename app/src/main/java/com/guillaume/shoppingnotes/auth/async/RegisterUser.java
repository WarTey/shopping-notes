package com.guillaume.shoppingnotes.auth.async;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.auth.async.interfaces.RegisterUserInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class RegisterUser extends AsyncTask<RegisterUserInterface, Void, Boolean> {

    private RegisterUserInterface mListener;
    private AppDatabase db;
    private String email;
    private User user;

    public RegisterUser(AppDatabase db, String email, User user) {
        this.db = db;
        this.email = email;
        this.user = user;
    }

    @Override
    protected Boolean doInBackground(RegisterUserInterface... registerUserInterfaces) {
        mListener = registerUserInterfaces[0];
        if (db.userDao().getUserByEmail(email) != null)
            return false;
        db.userDao().insertUser(user);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean request) {
        if (request)
            mListener.userRegistered();
        else
            mListener.userNonRegistered();
    }
}
