package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.guillaume.shoppingnotes.model.HasForGroup;

@Dao
public interface HasForGroupDao {

    @Insert
    void insertHasForGroup(HasForGroup hasForGroup);
}
