package com.guillaume.shoppingnotes.database.async.interfaces;

import com.guillaume.shoppingnotes.model.List;

public interface ListsInterface {

    void listsResponse(java.util.List<List> lists);

    void groupsListResponse(java.util.List<List> lists);

}
