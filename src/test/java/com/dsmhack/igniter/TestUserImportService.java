package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class TestUserImportService {

    private UserImportService userImportService;
    private String validFileName = "test_file.txt";
    private String validFilePath = "src/test/resources/" + validFileName;
    private String exampleUserFilePath = "src/test/resources/exampleUser.csv";
    private String invalidFilePath = "anInvalidFilePath.txt";
    private String[] expectedFileContent = {"cat 1, cat 2, gibberish", "stewie,squiggs,sherpa" };

    @Test
    public void testGetFileAsString_returnsNullWhenNoFileAndNotThrow() {
        this.userImportService = new UserImportService();
        try {
            Stream<String> actualFile = this.userImportService.getFileAsStringStream(invalidFilePath);
            assertNull(actualFile);
        } catch (Exception ex) {
            fail("un-expected, exception hit");
        }
    }

    @Test
    public void testGetFileAsString_returnsValidFileAsString() {
        this.userImportService = new UserImportService();
        Stream<String> actualFileContents = this.userImportService.getFileAsStringStream(validFilePath);
        assertEquals("file contents should match", expectedFileContent[1], actualFileContents.toArray()[1]);
    }

    @Test
    public void testLoadUsers_shouldReturnAnEmptyListWhenInvalidFilePath(){
        this.userImportService = new UserImportService();
        List<User> users = this.userImportService.getUsersByList("anInvalidFilePath");

        assert users.size() == 0;
    }

    @Test
    public void testLoadUsers_shouldReturnAListWithOneUser() {
        this.userImportService = new UserImportService();
        List<User> users = this.userImportService.getUsersByList(validFilePath);

        assert users.size() == 1;
    }

    @Test
    public void testParseStringIntoUser_returnsNullOnFailedParse() {
        this.userImportService = new UserImportService();
        Stream<String> exampleUserFile = this.userImportService.getFileAsStringStream(exampleUserFilePath);
        Object[] examplesUsersFromFile = exampleUserFile.toArray();
        User user = this.userImportService.parseStringIntoUser(examplesUsersFromFile[1].toString());

        assertEquals("john", user.getFirstName());
    }

}
