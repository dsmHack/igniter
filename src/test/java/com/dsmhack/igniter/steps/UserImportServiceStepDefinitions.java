package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.user.UserImportService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


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

}
