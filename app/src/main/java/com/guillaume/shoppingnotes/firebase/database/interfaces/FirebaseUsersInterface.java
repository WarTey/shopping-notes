package com.guillaume.shoppingnotes.firebase.database.interfaces;

import android.support.design.widget.TextInputLayout;

import com.guillaume.shoppingnotes.model.User;

import java.util.List;

public interface FirebaseUsersInterface {

    void firebaseUserResponse(User user);

    void firebaseUserUpdated(User user);

    void firebaseGroupUsersResponse(List<User> users);

    void firebaseUniqueGroupUserResponse(int index, TextInputLayout inputListName, com.guillaume.shoppingnotes.model.List list, User user);
}
