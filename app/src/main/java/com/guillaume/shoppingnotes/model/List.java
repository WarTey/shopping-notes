package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "lists",
        indices = {@Index(value = "user_id"), @Index(value = "group_id")},
        foreignKeys = {
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
            @ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "group_id")
        })
public class List {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    @ColumnInfo(name = "is_done")
    private boolean done;

    @ColumnInfo(name = "user_id")
    @Nullable
    private String userId;

    @ColumnInfo(name = "group_id")
    @Nullable
    private String groupId;

    public List() { }

    @Ignore
    public List(@NonNull String id, String name, boolean done, @Nullable String userId, @Nullable String groupId) {
        this.id = id;
        this.name = name;
        this.done = done;
        this.userId = userId;
        this.groupId = groupId;
    }

    @NonNull
    public String getId() { return id; }

    public void setId(@NonNull String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean getDone() { return done; }

    public void setDone(boolean done) { this.done = done; }

    @Nullable
    public String getUserId() { return userId; }

    public void setUserId(@Nullable String userId) { this.userId = userId; }

    @Nullable
    public String getGroupId() { return groupId; }

    public void setGroupId(@Nullable String groupId) { this.groupId = groupId; }
}
