package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserImportService {

    public List<String> getFileAsStringStream(String filePath) {
        Path resolvedFilePath;
        List<String> output = new ArrayList<String>();
        try {
            resolvedFilePath = Paths.get(filePath);
            Stream<String> linesStream = java.nio.file.Files.lines(resolvedFilePath);
            linesStream.forEach(line -> output.add(line));
            linesStream.close();
            return output;


        } catch (IOException e) {
            System.out.println("Error: invalid path");
            return null;
        }
    }

    public List<User> getUsersByList(String filepath) {
        List<String> userLines = this.getFileAsStringStream(filepath);
        if (userLines != null){
            userLines.remove(0); //remove header
            List<User> users = new ArrayList<User>();
            userLines.forEach(userInfo -> {
                users.add(parseStringIntoUser(userInfo));
            });

            return users;
        }
        return null;
    }

    public User parseStringIntoUser(String userInfo) {
        String[] userParts = userInfo.split(",");
        User parsedUser = new User(userParts[0], userParts[1], userParts[2], userParts[20]);
        return parsedUser;
    }
}
