package com.guillaume.shoppingnotes.database.async;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.async.interfaces.RegisterUserInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class RegisterUser extends AsyncTask<RegisterUserInterface, Void, Void> {

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
    protected Void doInBackground(RegisterUserInterface... registerUserInterfaces) {
        mListener = registerUserInterfaces[0];
        db.userDao().insertUser(user);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) { mListener.userRegistered(); }
}
