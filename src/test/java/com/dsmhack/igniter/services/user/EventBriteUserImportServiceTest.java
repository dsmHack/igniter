package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EventBriteUserImportServiceTest {

    private static final String USER_FILE_PATH = "src/test/resources/exampleUser.csv";

    private EventBriteUserImportService userImportService;
    private List<User> expectedUsers;

    @Before
    public void before() {
        expectedUsers = new ArrayList<User>();
        expectedUsers.add(new User("john", "doe", "aEmail", "aGithubUserName"));
        expectedUsers.add(new User("Stewie", "Rolek", "stewie@email.com", "stewieGithub"));
        expectedUsers.add(new User("LilSquiggle", "Rolek", "lilsquiggle@email.com", "lilsquiggleGithub"));
        userImportService = new EventBriteUserImportService();
    }

    @Test
    public void testLoadUsers_shouldReturnAListOfUsersWhenValidFilePath() throws UserImportException, FileNotFoundException {
        List<User> actualUsers = this.userImportService.getUsers(new FileReader(USER_FILE_PATH));
        assertEquals("Got matching lists of Users", expectedUsers, actualUsers);
    }

    @Test
    public void testParseStringIntoUser_returnsAUser() throws UserImportException, FileNotFoundException {
        User user = this.userImportService.getUsers(new FileReader(USER_FILE_PATH)).get(0);

        assertEquals("john", user.getFirstName());
        assertEquals("doe", user.getLastName());
        assertEquals("aEmail", user.getEmail());
        assertEquals("aGithubUserName", user.getGithubUsername());
    }

}
