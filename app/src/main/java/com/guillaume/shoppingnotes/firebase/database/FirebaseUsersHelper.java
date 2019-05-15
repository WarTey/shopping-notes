package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseUsersInterface;
import com.guillaume.shoppingnotes.model.User;

public class FirebaseUsersHelper {

    private FirebaseUsersInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseUsersHelper(FirebaseUsersInterface firebaseUsersInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseUsersInterface;
        databaseReference = firebaseDatabase.getReference("users");
    }

    public void getUser() {
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { mListener.firebaseUserResponse(dataSnapshot.getValue(User.class)); }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
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
