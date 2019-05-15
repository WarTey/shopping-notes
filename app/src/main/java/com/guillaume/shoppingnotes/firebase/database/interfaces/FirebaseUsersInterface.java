package com.guillaume.shoppingnotes.firebase.database.interfaces;

import com.guillaume.shoppingnotes.model.User;

public interface FirebaseUsersInterface {

    void firebaseUserCreated();

    void firebaseUserNonCreated();

    void firebaseUserResponse(User user);
}
