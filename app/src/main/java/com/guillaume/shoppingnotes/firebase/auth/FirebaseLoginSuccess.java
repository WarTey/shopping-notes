package com.guillaume.shoppingnotes.firebase.auth;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseLoginInterface;

public class FirebaseLoginSuccess implements OnSuccessListener<AuthResult> {

    private FirebaseLoginInterface mListener;

    public FirebaseLoginSuccess(FirebaseLoginInterface firebaseLoginUserInterface) {
        mListener = firebaseLoginUserInterface;
    }

    @Override
    public void onSuccess(AuthResult authResult) {

    }
}
