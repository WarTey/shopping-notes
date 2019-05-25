package com.guillaume.shoppingnotes.database.async.interfaces;

import com.guillaume.shoppingnotes.model.Item;

import java.util.List;

public interface ItemsInterface {

    void itemCreated(Item item);

    void itemsResponse(List<Item> items);
}
