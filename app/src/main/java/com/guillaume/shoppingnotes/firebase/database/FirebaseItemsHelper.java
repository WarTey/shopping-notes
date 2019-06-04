package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseItemsInterface;
import com.guillaume.shoppingnotes.model.Item;

import java.util.ArrayList;
import java.util.List;

public class FirebaseItemsHelper {

    private FirebaseItemsInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseItemsHelper(FirebaseItemsInterface firebaseItemsInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseItemsInterface;
        databaseReference = firebaseDatabase.getReference("items");
    }

    public void getItems() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Item> items = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
                    items.add(keyNode.getValue(Item.class));
                mListener.firebaseItemsResponse(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void createItem(final Item item) {
        databaseReference.child(item.getId()).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { mListener.firebaseItemCreated(item); }
            });
    }
}
