package com.dsmhack.igniter;

import com.dsmhack.igniter.services.UserImportService;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class TestUserImportService {

    private UserImportService userImportService;
    private String validFileName = "test_file.txt";
    private String validFilePath = "src/test/resources/" + validFileName;
    private String invalidFilePath = "anInvalidFilePath.txt";
    private String[] expectedFileContent = {"cat 1, cat 2, gibberish", "stewie,squiggs,sherpa" };

    @Test
    public void testGetFileAsString_ReturnsNullWhenNoFileAndNotThrow() {
        this.userImportService = new UserImportService();
        try {
            Stream<String> actualFile = this.userImportService.getFileAsString(invalidFilePath);
            assertNull(actualFile);
        } catch (Exception ex) {
            fail("un-expected, exception hit");
        }
    }

    @Test
    public void testGetFileAsString_ReturnsValidFileAsString() {
        this.userImportService = new UserImportService();
        Stream<String> actualFileContents = this.userImportService.getFileAsString(validFilePath);
        assertEquals("file contents should match", expectedFileContent[1], actualFileContents.toArray()[1]);
    }
}
