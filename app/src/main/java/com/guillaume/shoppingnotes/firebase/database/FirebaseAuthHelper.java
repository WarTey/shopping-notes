package com.guillaume.shoppingnotes.firebase.database;

import com.google.android.gms.tasks.OnSuccessListener;
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
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { mListener.firebaseUserCreated(); }
            });
    }
}
