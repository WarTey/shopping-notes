package com.guillaume.shoppingnotes.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.guillaume.shoppingnotes.database.dao.GroupDao;
import com.guillaume.shoppingnotes.database.dao.HasForItemDao;
import com.guillaume.shoppingnotes.database.dao.HasForUserDao;
import com.guillaume.shoppingnotes.database.dao.ItemDao;
import com.guillaume.shoppingnotes.database.dao.ListDao;
import com.guillaume.shoppingnotes.database.dao.UserDao;
import com.guillaume.shoppingnotes.model.Group;
import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.HasForUser;
import com.guillaume.shoppingnotes.model.Item;
import com.guillaume.shoppingnotes.model.List;
import com.guillaume.shoppingnotes.model.User;

@Database(entities = {User.class, List.class, Item.class, HasForUser.class, HasForItem.class, Group.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "shopping_notes";
    private static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null)
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
            }
        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract ListDao listDao();

    public abstract GroupDao groupDao();

    public abstract ItemDao itemDao();

    public abstract HasForItemDao hasForItemDao();

    public abstract HasForUserDao hasForUserDao();
}
