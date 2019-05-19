package com.guillaume.shoppingnotes.database.async.interfaces;

import com.guillaume.shoppingnotes.model.HasForItem;
import com.guillaume.shoppingnotes.model.List;

public interface HasForItemsInterface {

    void hasForItemsResponse(java.util.List<HasForItem> hasForItems);

    void hasForItemsDeleted(List list);
}
