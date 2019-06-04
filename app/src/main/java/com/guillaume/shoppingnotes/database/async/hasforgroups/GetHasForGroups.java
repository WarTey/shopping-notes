package com.guillaume.shoppingnotes.database.async.hasforgroups;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.HasForGroupsInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;

import java.util.List;

public class GetHasForGroups extends AsyncTask<HasForGroupsInterface, Void, List<HasForGroup>> {

    private HasForGroupsInterface mListener;
    private AppDatabase db;

    public GetHasForGroups(AppDatabase db) {
        this.db = db;
    }

    @Override
    protected java.util.List<HasForGroup> doInBackground(HasForGroupsInterface... hasForGroupsInterfaces) {
        mListener = hasForGroupsInterfaces[0];
        return db.hasForGroupDao().getHasForGroups();
    }

    @Override
    protected void onPostExecute(List<HasForGroup> hasForGroups) { mListener.hasForGroupsResponse(hasForGroups); }
}
