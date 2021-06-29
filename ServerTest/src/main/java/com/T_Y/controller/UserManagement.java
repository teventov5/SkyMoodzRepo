package com.T_Y.controller;

import com.T_Y.model.User;

public class UserManagement {

    public String[] showFavorites(User tempUser) {
        return tempUser.getFavoritesArr();
    }

}
