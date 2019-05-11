package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "groups",
        indices = {@Index(value = {"user_id"})},
        foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Group {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String name;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    public Group(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}
