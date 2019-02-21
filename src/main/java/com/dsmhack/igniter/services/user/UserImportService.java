package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;

import java.io.Reader;
import java.util.List;

public interface UserImportService {
  List<User> getUsers(Reader reader) throws UserImportException;
}
