package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseAuthInterface;
import com.guillaume.shoppingnotes.model.User;

public class FirebaseAuthHelper {

    private FirebaseAuthInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseAuthHelper(FirebaseAuthInterface firebaseAuthInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseAuthInterface;
        databaseReference = firebaseDatabase.getReference("users");
    }

    public void createUser(User user) {
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) mListener.firebaseUserCreated();
                else mListener.firebaseUserNonCreated();
                }
            });
    }
}
