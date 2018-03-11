package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.SpringContextConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserImportServiceStepDefinitions  {
    private UserImportService userImportService;
    private ArrayList<User> expectedUser;

    @When("the service is started it loads the default config file of \"user.csv\"")
    public void aServiceNeedsConfigData() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        userImportService = new UserImportService();
    }

    @Given("^a user.csv file is present$")
    public void aUserCsvFileIsPresent() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @When("^the getUsers is called$")
    public void theUesrFileIsReadAndaUserIsInTheUserArray() throws Throwable {
        userImportService = new UserImportService();
        expectedUser = new ArrayList<>();
        expectedUser.add(getExpectedUser());
        ArrayList<User> actualUsers = userImportService.getUsers();
        //message, expected, actual
        assertEquals("should be one user", expectedUser, actualUsers.get(0));
    }

    private User getExpectedUser() {
        User expectedUser = new User();
        expectedUser.setFirstName("john");
        expectedUser.setLastName("doe");
        expectedUser.setEmail("anEmail");
        expectedUser.setGithubUsername("aGithubUserName");
        return expectedUser;
    }
}
