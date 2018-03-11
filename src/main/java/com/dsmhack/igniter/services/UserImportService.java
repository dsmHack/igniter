package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class UserImportService {

    private String userImportFilePath;

    private ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

//    public UserImportService(@Value("${dsmhack.igniter.user.import.path:exampleUser.csv}") String userImportFilePath) {
//        this.userImportFilePath = userImportFilePath;
//    }

//    public void loadUsers() throws FileNotFoundException {
//        File file = new File(this.userImportFilePath);
//        if(file.exists()){
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//
//        }
//    }
}
