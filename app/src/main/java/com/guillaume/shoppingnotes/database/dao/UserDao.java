package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.guillaume.shoppingnotes.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT id FROM users WHERE email = :email")
    String getUserId(String email);

    @Insert
    void insertUser(User... user);

    @Update
    void updateUser(User... user);
}
