package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "lists",
        indices = {@Index(value = "user_id"), @Index(value = "group_id")},
        foreignKeys = {
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
            @ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "group_id")
        })
public class List {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    @ColumnInfo(name = "is_done")
    private boolean isDone;

    private long checked;

    @ColumnInfo(name = "user_id")
    @Nullable
    private Long userId;

    @ColumnInfo(name = "group_id")
    @Nullable
    private Long groupId;

    public List(String name, boolean isDone, long checked, @Nullable Long userId, @Nullable Long groupId) {
        this.name = name;
        this.isDone = isDone;
        this.checked = checked;
        this.userId = userId;
        this.groupId = groupId;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isDone() { return isDone; }

    public void setDone(boolean done) { isDone = done; }

    public long getChecked() { return checked; }

    public void setChecked(long checked) { this.checked = checked; }

    @Nullable
    public Long getUserId() { return userId; }

    public void setUserId(@Nullable Long userId) { this.userId = userId; }

    @Nullable
    public Long getGroupId() { return groupId; }

    public void setGroupId(@Nullable Long groupId) { this.groupId = groupId; }
}
