package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseListsInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.List;

import java.util.ArrayList;

public class FirebaseListsHelper {

    private FirebaseListsInterface mListener;
    private DatabaseReference databaseReference;

    public FirebaseListsHelper(FirebaseListsInterface firebaseListsInterface, FirebaseDatabase firebaseDatabase) {
        mListener = firebaseListsInterface;
        databaseReference = firebaseDatabase.getReference("lists");
    }

    public void getLists(final boolean history) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                java.util.List<List> lists = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    List list = keyNode.getValue(List.class);
                    if (list != null && list.getUserId() != null && list.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && list.getDone() == history)
                        lists.add(keyNode.getValue(List.class));
                }
                mListener.firebaseListsResponse(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void getGroupsList(final java.util.List<HasForGroup> hasForGroups) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                java.util.List<List> lists = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    List list = keyNode.getValue(List.class);
                    for (HasForGroup hasForGroup: hasForGroups)
                        if (list != null && hasForGroup.getListId().equals(list.getId()) && list.getUserId() == null)
                            lists.add(keyNode.getValue(List.class));
                }
                mListener.firebaseGroupsListResponse(lists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void createList(String listName, boolean group) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String foreignKey = userId;
        String groupId = "";
        if (group) {
            groupId = "(group)";
            foreignKey = null;
        }
        final List list = new List(listName + userId + groupId, listName, false, foreignKey);
        databaseReference.child(listName + userId + groupId).setValue(list)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { mListener.firebaseListCreated(list); }
                });
    }

    public void updateList(final List list) {
        databaseReference.child(list.getId()).setValue(list)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { mListener.firebaseListUpdated(list); }
            });
    }

    public void deleteList(final List list) {
        databaseReference.child(list.getId()).setValue(null)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { mListener.firebaseListDeleted(list); }
            });
    }
}