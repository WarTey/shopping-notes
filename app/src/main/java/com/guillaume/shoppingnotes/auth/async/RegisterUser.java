package com.guillaume.shoppingnotes.auth.async;

import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;

import com.guillaume.shoppingnotes.auth.async.interfaces.RegisterUserInterface;
import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.model.User;

public class RegisterUser extends AsyncTask<TextInputLayout, Void, TextInputLayout> {

    private RegisterUserInterface mListener;
    private AppDatabase db;
    private User user;

    public RegisterUser(RegisterUserInterface registerUserInterfaces, AppDatabase db, User user) {
        this.mListener = registerUserInterfaces;
        this.db = db;
        this.user = user;
    }

    @Override
    protected TextInputLayout doInBackground(TextInputLayout... textInputLayouts) {
        if (db.userDao().getUserByEmail(textInputLayouts[0].getEditText().getText().toString().trim()) != null)
            return textInputLayouts[0];
        db.userDao().insertUser(user);
        return null;
    }

    @Override
    protected void onPostExecute(TextInputLayout textInputLayout) {
        if (textInputLayout == null)
            mListener.userRegistered();
        else
            mListener.userNonRegistered(textInputLayout);
    }
}
