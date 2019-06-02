package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "has_for_groups",
        indices = {@Index(value = {"list_id"}), @Index(value = {"user_id"})},
        primaryKeys = {"list_id", "user_id"},
        foreignKeys = {
            @ForeignKey(entity = List.class, parentColumns = "id", childColumns = "list_id"),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
        })
public class HasForGroup {

    private boolean owner;

    private boolean status;

    @ColumnInfo(name = "list_id")
    @NonNull
    private String listId;

    @ColumnInfo(name = "user_id")
    @NonNull
    private String userId;

    public HasForGroup(@NonNull String listId, @NonNull String userId, boolean status, boolean owner) {
        this.listId = listId;
        this.userId = userId;
        this.status = status;
        this.owner = owner;
    }

    @NonNull
    public String getListId() { return listId; }

    public void setListId(@NonNull String listId) { this.listId = listId; }

    @NonNull
    public String getUserId() { return userId; }

    public void setUserId(@NonNull String userId) { this.userId = userId; }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }

    public boolean isOwner() { return owner; }

    public void setOwner(boolean owner) { this.owner = owner; }
}
