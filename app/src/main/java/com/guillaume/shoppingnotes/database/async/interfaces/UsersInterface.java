package com.guillaume.shoppingnotes.database.async.interfaces;

import com.guillaume.shoppingnotes.model.User;

public interface UsersInterface {

    void userCreated();

    void userResponse(User user);
}
