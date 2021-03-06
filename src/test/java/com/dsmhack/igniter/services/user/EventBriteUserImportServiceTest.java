package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventBriteUserImportServiceTest {

  private static final String USER_FILE_PATH = "src/test/resources/mock-event-brite-users.csv";

  private EventBriteUserImportService userImportService;

  @Before
  public void before() {
    userImportService = new EventBriteUserImportService();
  }

  @Test
  public void testGetUsers() throws UserImportException, FileNotFoundException {
    List<User> actualUsers = this.userImportService.getUsers(new FileReader(USER_FILE_PATH));
    assertEquals("Got matching lists of Users", MockUsers.getUsers(), actualUsers);
  }

}
