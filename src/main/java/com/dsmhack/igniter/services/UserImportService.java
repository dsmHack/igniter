package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserImportService {

    public Stream<String> getFileAsStringStream(String filePath) {
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

    public List<User> getUsersByList(String filepath) {
        List<User> users = new ArrayList<>();

        Stream<String> usersStream = this.getFileAsStringStream(filepath);
        if (usersStream == null){
            return users;
        } else {
            users.add(new User());
        }
        return users;
    }

    public User parseStringIntoUser(String userInfo) {
        User parsedUser = new User();
        String[] userParts = userInfo.split(",");
        parsedUser.setFirstName(userParts[0]);

        return parsedUser;
    }
}
