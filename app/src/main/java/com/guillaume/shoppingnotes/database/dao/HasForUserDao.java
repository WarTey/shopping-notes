package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.guillaume.shoppingnotes.model.HasForUser;

@Dao
public interface HasForUserDao {

    @Insert
    void insertHasForUser(HasForUser hasForUser);
}
