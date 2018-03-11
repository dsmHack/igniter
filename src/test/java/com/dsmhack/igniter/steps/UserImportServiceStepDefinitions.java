package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.services.UserImportService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserImportServiceStepDefinitions {

    @When("the service is started it loads the default config file of \"user.csv\"")
    public void aServiceNeedsConfigData() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        UserImportService userImportService = new UserImportService();
        assertEquals(userImportService.config_data, "things");
    }

    @Given("^a user.csv file is present$")
    public void aUserCsvFileIsPresent() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

}
