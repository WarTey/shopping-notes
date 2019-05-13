package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseLoginInterface;

public class FirebaseLoginFailure implements OnFailureListener {

    private FirebaseLoginInterface mListener;

    public FirebaseLoginFailure(FirebaseLoginInterface firebaseLoginInterface) { this.mListener = firebaseLoginInterface; }

    @Override
    public void onFailure(@NonNull Exception e) { mListener.firebaseNonLogged(); }
}
