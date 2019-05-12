package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.guillaume.shoppingnotes.model.List;

@Dao
public interface ListDao {

    @Insert
    void insertList(List list);
}
