package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseEditInterface;
import com.guillaume.shoppingnotes.model.User;

public class FirebaseEditEmail implements OnCompleteListener<Void> {

    private FirebaseEditInterface mListener;
    private User user;

    public FirebaseEditEmail(FirebaseEditInterface firebaseEditInterface, User user) {
        mListener = firebaseEditInterface;
        this.user = user;
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful())
            mListener.firebaseEmailEdited(user);
        else
            mListener.firebaseEmailNonEdited();
    }
}
