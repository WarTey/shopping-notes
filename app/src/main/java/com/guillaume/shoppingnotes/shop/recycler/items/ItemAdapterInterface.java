package com.guillaume.shoppingnotes.shop.recycler.items;

import android.widget.ImageView;

import com.guillaume.shoppingnotes.model.Item;

public interface ItemAdapterInterface {

    void addItemToList(Item item);

    void checkItem(Item item, ImageView checked);
}
