package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "has_for_users",
        indices = {@Index(value = {"group_id"}), @Index(value = {"user_id"})},
        primaryKeys = {"group_id", "user_id"},
        foreignKeys = {
            @ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "group_id"),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
        })
public class HasForUser {

    @ColumnInfo(name = "group_id")
    private Long groupId;

    @ColumnInfo(name = "user_id")
    private Long userId;

    private boolean status;

    public HasForUser(Long groupId, Long userId, boolean status) {
        this.groupId = groupId;
        this.userId = userId;
        this.status = status;
    }

    public Long getGroupId() { return groupId; }

    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }
}
