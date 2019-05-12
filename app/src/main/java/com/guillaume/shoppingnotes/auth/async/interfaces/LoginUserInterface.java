package com.guillaume.shoppingnotes.auth.async.interfaces;

import com.guillaume.shoppingnotes.model.User;

import java.util.List;

public interface LoginUserInterface {

    void userLogin(List<User> users);
}
