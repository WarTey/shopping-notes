package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.guillaume.shoppingnotes.model.HasForItem;

import java.util.List;

@Dao
public interface HasForItemDao {

    @Query("SELECT * FROM has_for_items")
    List<HasForItem> getHasForItems();

    @Insert
    void insertHasForItem(HasForItem hasForItem);
}
