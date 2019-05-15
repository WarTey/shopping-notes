package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseListsInterface;
import com.guillaume.shoppingnotes.model.List;

import java.util.ArrayList;

public class FirebaseListsHelper {

    private FirebaseListsInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseListsHelper(FirebaseListsInterface firebaseGetListsInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseGetListsInterface;
        databaseReference = firebaseDatabase.getReference("lists");
    }

    public void getLists() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                java.util.List<List> lists = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    List list = keyNode.getValue(List.class);
                    if (list != null && list.getUserId() != null && list.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        lists.add(keyNode.getValue(List.class));
                }
                mListener.firebaseListsResponse(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}