package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.guillaume.shoppingnotes.model.List;

@Dao
public interface ListDao {

    @Query("SELECT * FROM lists WHERE user_id = :userId AND is_done = :done")
    java.util.List<List> getListsByUserId(String userId, boolean done);

    @Insert
    void insertList(List list);

    @Update
    void updateList(List... lists);

    @Delete
    void deleteList(List... lists);
}
