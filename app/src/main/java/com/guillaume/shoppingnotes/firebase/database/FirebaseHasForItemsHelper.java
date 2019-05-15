package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseHasForItemsInterface;
import com.guillaume.shoppingnotes.model.HasForItem;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHasForItemsHelper {

    private FirebaseHasForItemsInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseHasForItemsHelper(FirebaseHasForItemsInterface firebaseHasForItemsInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseHasForItemsInterface;
        databaseReference = firebaseDatabase.getReference("has_for_items");
    }

    public void getHasForItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HasForItem> hasForItems = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
                    hasForItems.add(keyNode.getValue(HasForItem.class));
                mListener.firebaseHasForItemsResponse(hasForItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
