package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.guillaume.shoppingnotes.model.HasForGroup;

import java.util.List;

@Dao
public interface HasForGroupDao {

    @Query("SELECT * FROM has_for_groups")
    List<HasForGroup> getHasForGroups();

    @Query("DELETE FROM has_for_groups WHERE list_id = :listId")
    void deleteHasForGroups(String listId);

    @Insert
    void insertHasForGroup(HasForGroup hasForGroup);
}
