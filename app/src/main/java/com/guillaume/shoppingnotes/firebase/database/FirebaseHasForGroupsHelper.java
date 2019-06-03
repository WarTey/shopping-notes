package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseHasForGroupsInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.HasForItem;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHasForGroupsHelper {

    private FirebaseHasForGroupsInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseHasForGroupsHelper(FirebaseHasForGroupsInterface firebaseHasForGroupsInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseHasForGroupsInterface;
        databaseReference = firebaseDatabase.getReference("has_for_groups");
    }

    public void getHasForGroups() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HasForGroup> hasForGroups = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    HasForGroup hasForGroup = keyNode.getValue(HasForGroup.class);
                    if (hasForGroup != null && hasForGroup.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        hasForGroups.add(hasForGroup);
                }
                mListener.firebaseHasForGroupsResponse(hasForGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
