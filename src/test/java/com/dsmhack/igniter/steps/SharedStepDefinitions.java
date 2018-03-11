package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.SpringContextConfiguration;
import com.dsmhack.igniter.services.GoogleDriveIntegrationService;
import com.dsmhack.igniter.services.TeamConfigurationService;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class SharedStepDefinitions extends SpringContextConfiguration {

    @Autowired
    private TeamConfigurationService teamConfigurationService;

    @When("^The Admin creates the team \"([^\"]*)\"$")
    public void theAdminCreatesTheTeam(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        teamConfigurationService.createTeam(teamName);



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

    @Given("^The integration service \"([^\"]*)\" is enabled$")
    public void theIntegrationServiceIsEnabled(String integrationServiceName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^The active integrations are checked$")
    public void theActiveIntegrationsAreChecked() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        throw new PendingException();
    }

    @Then("^The only active integration service is \"([^\"]*)\"$")
    public void theOnlyActiveIntegrationServiceIs(String integrationServiceName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^The integration service <IntegrationServiceName> is enabled$")
    public void theIntegrationServiceIntegrationServiceNameIsEnabled() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
