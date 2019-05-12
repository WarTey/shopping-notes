package com.guillaume.shoppingnotes.firebase.auth;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseRegisterInterface;

public class FirebaseRegisterSuccess implements OnSuccessListener<AuthResult> {

    private FirebaseRegisterInterface mListener;

    public FirebaseRegisterSuccess(FirebaseRegisterInterface firebaseRegisterUserInterface) { mListener = firebaseRegisterUserInterface; }

    @Override
    public void onSuccess(AuthResult authResult) { mListener.firebaseRegistered(); }
}
