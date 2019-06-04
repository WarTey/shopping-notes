package com.guillaume.shoppingnotes.shop.recycler.lists;

import com.guillaume.shoppingnotes.model.List;

public interface ListAdapterInterface {

    void seeItems(List list);

    void seeMembers(List list);

    void addItemsToList(List list);

    void initAlert(List list, boolean addMember);

    void removeList(List list);

    void historyList(List list);

    void noHistoryList(List list);

    void acceptInvit(List list);
}
