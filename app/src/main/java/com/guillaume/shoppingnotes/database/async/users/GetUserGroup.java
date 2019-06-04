package com.guillaume.shoppingnotes.database.async.users;

import android.os.AsyncTask;

import com.guillaume.shoppingnotes.database.AppDatabase;
import com.guillaume.shoppingnotes.database.async.interfaces.UsersGroupInterface;
import com.guillaume.shoppingnotes.database.async.interfaces.UsersInterface;
import com.guillaume.shoppingnotes.model.HasForGroup;
import com.guillaume.shoppingnotes.model.User;

import java.util.ArrayList;
import java.util.List;

public class GetUserGroup extends AsyncTask<UsersGroupInterface, Void, List<User>> {

    private UsersGroupInterface mListener;
    private AppDatabase db;
    private List<HasForGroup> hasForGroups;

    public GetUserGroup(AppDatabase db, List<HasForGroup> hasForGroups) {
        this.hasForGroups = hasForGroups;
        this.db = db;
    }

    @Override
    protected List<User> doInBackground(UsersGroupInterface... usersGroupInterfaces) {
        mListener = usersGroupInterfaces[0];
        List<User> users = new ArrayList<>();
        for (HasForGroup hasForGroup: hasForGroups)
            users.add(db.userDao().getUserById(hasForGroup.getUserId()));
        return users;
    }

    @Override
    protected void onPostExecute(List<User> users) { mListener.userGroupResponse(users); }
}
