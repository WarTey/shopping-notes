package com.guillaume.shoppingnotes.firebase.database;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseItemsInterface;
import com.guillaume.shoppingnotes.model.Item;

public class FirebaseItemsHelper {

    private FirebaseItemsInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseItemsHelper(FirebaseItemsInterface firebaseItemsInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseItemsInterface;
        databaseReference = firebaseDatabase.getReference("items");
    }

    public void createItem(final Item item) {
        databaseReference.child(item.getId()).setValue(item)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { mListener.firebaseItemCreated(item); }
            });
    }
}
