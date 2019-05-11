package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = "has_for_items",
        indices = {@Index(value = {"list_id"}), @Index(value = {"item_id"})},
        primaryKeys = {"list_id", "item_id"},
        foreignKeys = {
            @ForeignKey(entity = List.class, parentColumns = "id", childColumns = "list_id"),
            @ForeignKey(entity = Item.class, parentColumns = "id", childColumns = "item_id")
        })
public class HasForItem {

    @ColumnInfo(name = "list_id")
    private long listId;

    @ColumnInfo(name = "item_id")
    private long itemId;

    public HasForItem(Integer listId, Integer itemId) {
        this.listId = listId;
        this.itemId = itemId;
    }

    public long getListId() { return listId; }

    public void setListId(long listId) { this.listId = listId; }

    public long getItemId() { return itemId; }

    public void setItemId(long itemId) { this.itemId = itemId; }
}
