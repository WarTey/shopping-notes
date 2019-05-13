package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseRegisterInterface;

public class FirebaseRegister implements OnCompleteListener<AuthResult> {

    private FirebaseRegisterInterface mListener;

    public FirebaseRegister(FirebaseRegisterInterface firebaseRegisterInterface) { mListener = firebaseRegisterInterface; }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) mListener.firebaseRegistered();
        else mListener.firebaseNonRegistered();
    }
}
