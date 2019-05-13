package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.guillaume.shoppingnotes.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Insert
    void insertUser(User user);
}
