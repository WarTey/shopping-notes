package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseRegisterInterface;
import com.guillaume.shoppingnotes.firebase.database.FirebaseUsersHelper;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseUsersInterface;
import com.guillaume.shoppingnotes.model.User;

public class FirebaseRegister implements OnCompleteListener<AuthResult>, FirebaseUsersInterface {

    private FirebaseRegisterInterface mListener;
    private User user;

    public FirebaseRegister(FirebaseRegisterInterface firebaseRegisterInterface, User user) {
        mListener = firebaseRegisterInterface;
        this.user = user;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            new FirebaseUsersHelper(this, FirebaseDatabase.getInstance()).createUser(user);
        } else mListener.firebaseNonRegistered();
    }

    @Override
    public void firebaseUserCreated() { mListener.firebaseRegistered(); }

    @Override
    public void firebaseUserNonCreated() { mListener.firebaseNonRegistered(); }

    @Override
    public void firebaseUserResponse(User user) { }
}
