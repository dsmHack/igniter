package com.dsmhack.igniter.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GoogleDriveIntegrationStepDefinitions {
    @Given("^The google drive api key is configured$")
    public void theGoogleDriveApiKeyIsConfigured() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The google drive secret key is configured$")
    public void theGoogleDriveSecretKeyIsConfigured() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The google Drive folder of \"([^\"]*)\" exists$")
    public void theGoogleDriveFolderOfExists(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }



    @And("^Google drive integration is enabled$")
    public void googleDriveIntegrationIsEnabled() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^The User is Added$")
    public void theUserIsAdded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Then("^The google drive folder for \"([^\"]*)\" should have the user \"([^\"]*)\" added with read rights$")
    public void theGoogleDriveFolderForShouldHaveTheUserAddedWithReadRights(String teamName, String googleEmail) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The google Drive folder of \"([^\"]*)\" is created$")
    public void theGoogleDriveFolderOfIsCreated(String folderPath) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
