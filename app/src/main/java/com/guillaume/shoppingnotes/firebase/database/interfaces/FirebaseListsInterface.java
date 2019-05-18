package com.guillaume.shoppingnotes.firebase.database.interfaces;

import com.guillaume.shoppingnotes.model.List;

public interface FirebaseListsInterface {

    void firebaseListsResponse(java.util.List<List> lists);

    void firebaseListCreated(List list);

    void firebaseListUpdated(List list);

    void firebaseListDeleted(List list);
}
