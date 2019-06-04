package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseHasForGroupsInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.User;

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
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
                    hasForGroups.add(keyNode.getValue(HasForGroup.class));
                mListener.firebaseHasForGroupsResponse(hasForGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void createHasForGroups(com.guillaume.shoppingnotes.model.List list) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final HasForGroup hasForGroup = new HasForGroup(list.getId(), userId, true, true);
            databaseReference.child(list.getName() + userId).setValue(hasForGroup);
        }
    }

    public void createHasForGroupsMember(final User user, com.guillaume.shoppingnotes.model.List list) {
        final HasForGroup hasForGroup = new HasForGroup(list.getId(), user.getId(), false, false);
        databaseReference.child(list.getName() + user.getId()).setValue(hasForGroup);
    }

    public void createHasForGroupsAccept(com.guillaume.shoppingnotes.model.List list) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final HasForGroup hasForGroup = new HasForGroup(list.getId(), userId, true, false);
            databaseReference.child(list.getName() + userId).setValue(hasForGroup);
        }
    }

    public void createHasForGroupsRefuse(com.guillaume.shoppingnotes.model.List list) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference.child(list.getName() + userId).setValue(null);
        }
    }

    public void createHasForGroupsDeleteMember(com.guillaume.shoppingnotes.model.List list, User user) {
        databaseReference.child(list.getName() + user.getId()).setValue(null);
    }

    public void deleteHasForGroups(final String listId) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    HasForGroup hasForGroup = keyNode.getValue(HasForGroup.class);
                    if (keyNode.getKey() != null && hasForGroup != null && hasForGroup.getListId().equals(listId))
                        databaseReference.child(keyNode.getKey()).setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
