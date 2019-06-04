package com.guillaume.shoppingnotes.firebase.database.interfaces;

import com.guillaume.shoppingnotes.model.HasForGroup;

import java.util.List;

public interface FirebaseHasForGroupsInterface {

    void firebaseHasForGroupsResponse(List<HasForGroup> hasForGroups);

    void firebaseHasForGroupsCreated(HasForGroup hasForGroup);

    void firebaseHasForGroupsMemberCreated(HasForGroup hasForGroup, String userEmail);
}
