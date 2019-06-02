package com.guillaume.shoppingnotes.firebase.auth.interfaces;

import com.guillaume.shoppingnotes.model.User;

public interface FirebaseEditInterface {

    void firebaseEmailEdited(User user);

    void firebaseEmailNonEdited();

    void firebasePasswordEdited(User user);

    void firebasePasswordNonEdited();
}
