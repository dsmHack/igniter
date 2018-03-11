package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserImportService {
    private ArrayList<User> users = new ArrayList<>();

    public UserImportService() {
        User fakeUser = new User();
        fakeUser.setFirstName("test");
        users.add(fakeUser);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
