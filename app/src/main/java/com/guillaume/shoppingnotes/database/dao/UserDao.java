package com.guillaume.shoppingnotes.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.guillaume.shoppingnotes.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users")
    List<User> getUsers();

    @Insert
    void insertUser(User user);
}
