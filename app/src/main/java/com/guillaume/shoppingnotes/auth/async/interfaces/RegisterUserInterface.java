package com.guillaume.shoppingnotes.auth.async.interfaces;

import android.support.design.widget.TextInputLayout;

public interface RegisterUserInterface {

    void userRegistered();

    void userNonRegistered(TextInputLayout textInputLayout);
}
