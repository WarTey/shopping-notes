package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseRegisterInterface;

public class FirebaseRegisterFailure implements OnFailureListener {

    private FirebaseRegisterInterface mListener;

    public FirebaseRegisterFailure(FirebaseRegisterInterface firebaseRegisterUserInterface) { mListener = firebaseRegisterUserInterface; }

    @Override
    public void onFailure(@NonNull Exception e) { mListener.firebaseNonRegistered(); }
}
