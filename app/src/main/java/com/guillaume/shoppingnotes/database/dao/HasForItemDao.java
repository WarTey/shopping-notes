package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.guillaume.shoppingnotes.model.HasForItem;

@Dao
public interface HasForItemDao {

    @Insert
    void insertHasForItem(HasForItem hasForItem);
}
