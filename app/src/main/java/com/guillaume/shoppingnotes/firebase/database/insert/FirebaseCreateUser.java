package com.guillaume.shoppingnotes.firebase.database.insert;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseCreateUserInterface;

public class FirebaseCreateUser implements OnCompleteListener<Void> {

    private FirebaseCreateUserInterface mListener;

    public FirebaseCreateUser(FirebaseCreateUserInterface firebaseUserInterface) { mListener = firebaseUserInterface; }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) mListener.firebaseUserCreated();
        else mListener.firebaseUserNonCreated();
    }
}
