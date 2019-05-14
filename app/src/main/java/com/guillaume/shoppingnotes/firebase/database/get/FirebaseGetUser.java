package com.guillaume.shoppingnotes.firebase.database.get;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseGetUserInterface;
import com.guillaume.shoppingnotes.model.User;

public class FirebaseGetUser implements ValueEventListener {

    private FirebaseGetUserInterface mListener;

    public FirebaseGetUser(FirebaseGetUserInterface firebaseGetUserInterface) { mListener = firebaseGetUserInterface; }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) { mListener.firebaseUserResponse(dataSnapshot.getValue(User.class)); }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) { }
}
