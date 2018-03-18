package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestUserImportService {

    private UserImportService userImportService;
    private String exampleUserFilePath = "src/test/resources/exampleUser.csv";
    private String invalidFilePath = "anInvalidFilePath.txt";
    private List<User> expectedUsers;

    @Before
    public void before() {
        expectedUsers = new ArrayList<User>();
        expectedUsers.add(new User("john", "doe", "aEmail", "aGithubUserName"));
        expectedUsers.add(new User("Stewie", "Rolek", "stewie@email.com", "stewieGithub"));
        expectedUsers.add(new User("LilSquiggle", "Rolek", "lilsquiggle@email.com", "lilsquiggleGithub"));
    }

    @Test
    public void testGetFileAsString_returnsNullWhenNoFileAndNotThrow() {
        this.userImportService = new UserImportService();
        try {
            List<String> actualFile = this.userImportService.getFileAsStringStream(invalidFilePath);
            assertNull(actualFile);
        } catch (Exception ex) {
            fail("un-expected, exception hit");
        }
    }

    @Test
    public void testGetFileAsString_returnsValidFileAsString() {
        this.userImportService = new UserImportService();
        List<String> actualFileContents = this.userImportService.getFileAsStringStream(exampleUserFilePath);
        assertEquals("file contents should match", "First Name,Last Name,", actualFileContents.get(0).substring(0, 21));
    }

    @Test
    public void testLoadUsers_shouldReturnANullWhenInvalidFilePath(){
        this.userImportService = new UserImportService();
        List<User> users = this.userImportService.getUsersByList("anInvalidFilePath");

        assert users == null;
    }

    @Test
    public void testLoadUsers_shouldReturnAListOfUsersWhenValidFilePath(){
        this.userImportService = new UserImportService();
        List<User> actualUsers = this.userImportService.getUsersByList(exampleUserFilePath);

        assertEquals("Got matching lists of Users", expectedUsers, actualUsers);
    }

    @Test
    public void testParseStringIntoUser_returnsAUser() {
        this.userImportService = new UserImportService();
        List<String> exampleUserFile = this.userImportService.getFileAsStringStream(exampleUserFilePath);
        Object[] examplesUsersFromFile = exampleUserFile.toArray();
        User user = this.userImportService.parseStringIntoUser(examplesUsersFromFile[1].toString());


        assertEquals("john", user.getFirstName());
        assertEquals("doe", user.getLastName());
        assertEquals("aEmail", user.getEmail());
        assertEquals("aGithubUserName", user.getGithubUsername());
    }

}
