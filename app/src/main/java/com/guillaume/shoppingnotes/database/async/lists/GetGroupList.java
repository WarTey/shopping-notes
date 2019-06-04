package com.guillaume.shoppingnotes.database.async.lists;

import android.os.AsyncTask;
import android.util.Log;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.ListsInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.List;

import java.util.ArrayList;

public class GetGroupList extends AsyncTask<ListsInterface, Void, java.util.List<List>> {

    private ListsInterface mListener;
    private java.util.List<HasForGroup> hasForGroups;
    private AppDatabase db;

    public GetGroupList(AppDatabase db, java.util.List<HasForGroup> hasForGroups) {
        this.db = db;
        this.hasForGroups = hasForGroups;
    }

    @Override
    protected java.util.List<List> doInBackground(ListsInterface... getListsInterfaces) {
        mListener = getListsInterfaces[0];
        java.util.List<List> lists = new ArrayList<>();
        for (HasForGroup hasForGroup: hasForGroups)
            lists.add(db.listDao().getGroupListById(hasForGroup.getListId()));
        return lists;
    }

    @Override
    protected void onPostExecute(java.util.List<List> lists) { mListener.groupsListResponse(lists); }
}
