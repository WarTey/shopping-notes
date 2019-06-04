package com.guillaume.shoppingnotes.database.async.interfaces;

import com.guillaume.shoppingnotes.model.User;

import java.util.List;

public interface UsersGroupInterface {

    void userGroupResponse(List<User> users);
}
