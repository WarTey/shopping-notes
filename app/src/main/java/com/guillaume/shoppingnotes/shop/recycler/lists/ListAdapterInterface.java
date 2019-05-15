package com.guillaume.shoppingnotes.shop.recycler.lists;

import com.guillaume.shoppingnotes.model.List;

public interface ListAdapterInterface {

    void initAlert(List list);

    void removeList(List list);

    void historyList(List list);
}
