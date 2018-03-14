package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<String> getFileAsString(String filePath) {
        Path resolvedFilePath;
        Stream<String> output;
        try {
            resolvedFilePath = Paths.get(filePath);
//            System.out.println("resolvedFile Path: " + resolvedFilePath.toAbsolutePath().toString());
            return java.nio.file.Files.lines(resolvedFilePath);
        } catch (IOException e) {
            System.out.println("Error: invalid path");
            return null;
        }
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
