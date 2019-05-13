package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseLoginInterface;

public class FirebaseLogin implements OnCompleteListener<AuthResult> {

    private FirebaseLoginInterface mListener;

    public FirebaseLogin(FirebaseLoginInterface firebaseLoginInterface) { mListener = firebaseLoginInterface; }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) mListener.firebaseLogged();
        else mListener.firebaseNonLogged();
    }
}
