package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "groups",
        indices = {@Index(value = {"user_id"})},
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Group {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    @ColumnInfo(name = "user_id")
    private String userId;

    public Group(@NonNull String id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @NonNull
    public String getId() { return id; }

    public void setId(@NonNull String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }
}
