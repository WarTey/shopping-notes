package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.guillaume.shoppingnotes.model.Group;

@Dao
public interface GroupDao {

    @Insert
    void insertGroup(Group group);
}
