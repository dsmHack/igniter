package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class UserImportServiceStepDefinitions  {

    @Autowired
    private UserImportService userImportService;
    private ArrayList<User> expectedUser;

    @When("the service is started it loads the default config file of \"user.csv\"")
    public void aServiceNeedsConfigData() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @Given("^a user.csv file is present$")
    public void aUserCsvFileIsPresent() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @When("^the getUsers is called the following user exists$")
    public void theUserFileIsReadAndaUserIsInTheUserArrayList(List<User> users) throws Throwable {
        User expectedUser = users.get(0);
        ArrayList<User> actualUsers = this.userImportService.getUsers();
        //message, expected, actual

        assertEquals("should be one user",1, userImportService.getUsers().size());
        assertEquals("should be user we created", expectedUser, actualUsers.get(0));
    }

    @Given("^The following users are created$")
    public void theFollowingUsersAreCreated(List<User> users    ) throws Throwable {
        this.userImportService = new UserImportService();
    }

    @When("^the service is started it loads the default config file of \"([^\"]*)\"$")
    public void theServiceIsStartedItLoadsTheDefaultConfigFileOf(String userConfigFile) throws Throwable {
//        this.userImportService = new UserImportService();
    }


    @When("^the getFileAsString is called with the following path \"([^\"]*)\"$")
    public void theGetFileAsStringIsCalledWithTheFollowingPath(String filePath) throws Throwable {
        String exampleFileContent = "sherpa";
        File fileMock = Mockito.mock(File.class);
        PowerMockito.whenNew(File.class).withArguments(filePath).thenReturn(fileMock);
        Mockito.when(fileMock.exists()).thenReturn(true);
        this.userImportService = new UserImportService();
        String actualFile = this.getFileAsString(filePath);
    }
}
