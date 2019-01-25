package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;

import java.util.List;

public interface UserImportService {
    List<User> getUsers(String filePath) throws UserImportException;
}
