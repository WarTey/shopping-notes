package com.guillaume.shoppingnotes.firebase.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.firebase.auth.interfaces.FirebaseRegisterInterface;
import com.guillaume.shoppingnotes.firebase.database.insert.FirebaseCreateUser;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseCreateUserInterface;
import com.guillaume.shoppingnotes.model.User;

public class FirebaseRegister implements OnCompleteListener<AuthResult>, FirebaseCreateUserInterface {

    private FirebaseRegisterInterface mListener;
    private User user;

    public FirebaseRegister(FirebaseRegisterInterface firebaseRegisterInterface, User user) {
        mListener = firebaseRegisterInterface;
        this.user = user;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful())
            FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                    .addOnCompleteListener(new FirebaseCreateUser(this));
        else mListener.firebaseNonRegistered();
    }

    @Override
    public void firebaseUserCreated() { mListener.firebaseRegistered(); }

    @Override
    public void firebaseUserNonCreated() { mListener.firebaseNonRegistered(); }
}
