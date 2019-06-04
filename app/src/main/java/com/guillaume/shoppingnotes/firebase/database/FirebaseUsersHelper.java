package com.guillaume.shoppingnotes.firebase.database;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.guillaume.shoppingnotes.firebase.database.interfaces.FirebaseUsersInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.User;

import java.util.ArrayList;
import java.util.List;

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

    public void updateUser(final User user) {
        databaseReference.child(user.getId()).setValue(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { mListener.firebaseUserUpdated(user); }
            });
    }

    public void getGroupUsers(final List<HasForGroup> hasForGroups) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User user = keyNode.getValue(User.class);
                    for (HasForGroup hasForGroup: hasForGroups)
                        if (user != null && hasForGroup.getUserId().equals(user.getId()))
                            users.add(keyNode.getValue(User.class));
                }
                mListener.firebaseGroupUsersResponse(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void GetUniqueUserForGroup(final List<HasForGroup> hasForGroups, final com.guillaume.shoppingnotes.model.List list, final String userEmail, final TextInputLayout inputListName) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 0;
                User user = new User();
                boolean userExist = false;
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    User userTemp = keyNode.getValue(User.class);
                    if (userTemp != null && userTemp.getEmail().equals(userEmail)) {
                        userExist = true;
                        user = userTemp;
                        for (HasForGroup hasForGroup: hasForGroups)
                            if (hasForGroup.getUserId().equals(userTemp.getId()) && hasForGroup.getListId().equals(list.getId()))
                                index += 1;
                    }
                }
                if (userExist)
                    mListener.firebaseUniqueGroupUserResponse(index, inputListName, list, user);
                else
                    mListener.firebaseUniqueGroupUserResponse(-1, inputListName, list, user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
