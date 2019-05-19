package com.guillaume.shoppingnotes.shop.recycler.lists;

import com.guillaume.shoppingnotes.model.List;

public interface ListAdapterInterface {

    void seeItems(List list);

    void addItemsToList(List list);

    void initAlert(List list);

    void removeList(List list);

    void historyList(List list);

    void noHistoryList(List list);
}
