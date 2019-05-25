package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.guillaume.shoppingnotes.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM items WHERE id = :itemId")
    Item getItemById(String itemId);

    @Query("SELECT * FROM items")
    List<Item> getItems();

    @Insert
    void insertItem(Item... item);

    @Update
    void updateItem(Item... items);
}
