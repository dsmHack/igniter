package com.dsmhack.igniter.steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;

public class SharedStepDefinitions {

    @When("^The Admin creates the team \"([^\"]*)\"$")
    public void theAdminCreatesTheTeam(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^The following user is added$")
    public void theFollowingUserIsAdded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^The following user is created$")
    public void theFollowingUserIsCreated(DataTable users) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the Admin assigns the user to \"([^\"]*)\"$")
    public void theAdminAssignsTheUserTo(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
