package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.SpringContextConfiguration;
import com.dsmhack.igniter.services.UserImportService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class UserImportServiceStepDefinitions  {
//    public com.dsmhack.igniter.steps.UserImportServiceStepDefinitions() {
//
//    }

    @When("the service is started it loads the default config file of \"user.csv\"")
    public void aServiceNeedsConfigData() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        UserImportService userImportService = new UserImportService();
        assert userImportService.config_data == true;
    }

    @Given("^a user.csv file is present$")
    public void aUserCsvFileIsPresent() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

//    @When("^The following user is added$")
//    public void theFollowingUserIsAdded() throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
}
