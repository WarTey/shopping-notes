package com.guillaume.shoppingnotes.firebase.database.interfaces;

import com.guillaume.shoppingnotes.model.HasForItem;

import java.util.List;

public interface FirebaseHasForItemsInterface {

    void firebaseHasForItemsResponse(List<HasForItem> hasForItems);
}
