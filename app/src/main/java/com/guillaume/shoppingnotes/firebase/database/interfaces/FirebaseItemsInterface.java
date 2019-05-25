package com.guillaume.shoppingnotes.firebase.database.interfaces;

import com.guillaume.shoppingnotes.model.Item;

import java.util.List;

public interface FirebaseItemsInterface {

    void firebaseItemCreated(Item item);

    void firebaseItemsResponse(List<Item> items);
}
