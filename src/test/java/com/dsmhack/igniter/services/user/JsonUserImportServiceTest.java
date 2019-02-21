package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonUserImportServiceTest {

  private static final String USER_FILE_PATH = "src/test/resources/mock-users.json";

  private JsonUserImportService userImportService;

  @Before
  public void before() {
    userImportService = new JsonUserImportService(new ObjectMapper());
  }

  @Test
  public void testGetUsers() throws UserImportException, FileNotFoundException {
    List<User> actualUsers = this.userImportService.getUsers(new FileReader(USER_FILE_PATH));
    assertEquals("Got matching lists of Users", MockUsers.getUsers(), actualUsers);
  }

}
