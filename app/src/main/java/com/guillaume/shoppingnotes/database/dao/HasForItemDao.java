package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.guillaume.shoppingnotes.model.HasForItem;

import java.util.List;

@Dao
public interface HasForItemDao {

    @Query("SELECT * FROM has_for_items")
    List<HasForItem> getHasForItems();

    @Query("SELECT * FROM has_for_items WHERE item_id = :itemId AND list_id = :listId")
    HasForItem getHasForItemsById(String itemId, String listId);

    @Query("DELETE FROM has_for_items WHERE list_id = :listId")
    void deleteHasForItems(String listId);

    @Insert
    void insertHasForItem(HasForItem... hasForItem);

    @Update
    void updateHasForItem(HasForItem... hasForItems);

    @Delete
    void deleteHasForItem(HasForItem... hasForItems);
}
