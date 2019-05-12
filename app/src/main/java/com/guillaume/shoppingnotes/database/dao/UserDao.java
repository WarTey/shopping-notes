package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.guillaume.shoppingnotes.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);
}
