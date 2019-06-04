package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "has_for_items",
        indices = {@Index(value = {"list_id"}), @Index(value = {"item_id"})},
        primaryKeys = {"list_id", "item_id"},
        foreignKeys = {
            @ForeignKey(entity = List.class, parentColumns = "id", childColumns = "list_id"),
            @ForeignKey(entity = Item.class, parentColumns = "id", childColumns = "item_id")
        })
public class HasForItem {

    private boolean checked;

    @ColumnInfo(name = "list_id")
    @NonNull
    private String listId;

    @ColumnInfo(name = "item_id")
    @NonNull
    private String itemId;

    public HasForItem() {
        listId = "";
        itemId = "";
    }

    @Ignore
    public HasForItem(boolean checked, @NonNull String listId, @NonNull String itemId) {
        this.checked = checked;
        this.listId = listId;
        this.itemId = itemId;
    }

    public boolean getChecked() { return checked; }

    public void setChecked(boolean checked) { this.checked = checked; }

    @NonNull
    public String getListId() { return listId; }

    public void setListId(@NonNull String listId) { this.listId = listId; }

    @NonNull
    public String getItemId() { return itemId; }

    public void setItemId(@NonNull String itemId) { this.itemId = itemId; }
}
