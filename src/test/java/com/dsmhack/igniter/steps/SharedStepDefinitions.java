package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.SpringContextConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationServicesRegistry;
import com.dsmhack.igniter.services.TeamConfigurationService;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertTrue;

public class SharedStepDefinitions extends SpringContextConfiguration {

    @Autowired
    private TeamConfigurationService teamConfigurationService;

    @Autowired
    IntegrationServicesRegistry integrationServicesRegistry;
    private User user;

    @When("^The Admin creates the team \"([^\"]*)\"$")
    public void theAdminCreatesTheTeam(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        teamConfigurationService.createTeam(teamName);
        User user = User.builder()
            .githubUsername("Kickercost")
            .slackEmail("joshua@kickercost.com")
            .build();
        teamConfigurationService.addUserToTeam(teamName, user);
    }

    @When("^The following user is added$")
    public void theFollowingUserIsAdded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^The following user is staged$")
    public void theFollowingUserIsStaged(User user) throws Throwable {
        this.user = user;
    }

    @When("^the Admin assigns the user to \"([^\"]*)\"$")
    public void theAdminAssignsTheUserTo(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^The integration service \"([^\"]*)\" is enabled$")
    public void theIntegrationServiceIsEnabled(String integrationServiceName) throws Throwable {
        integrationServicesRegistry.activateIntegrationService(integrationServiceName);
    }

    @Then("^The active integration services contain \"([^\"]*)\"$")
    public void theOnlyActiveIntegrationServiceIs(String integrationServiceName) throws Throwable {
        assertTrue( integrationServicesRegistry.getActiveIntegrationServices().stream().anyMatch(
            integrationService -> integrationService.getIntegrationServiceName().equals(integrationServiceName)
        ));
    }


    @And("^The staged user is added to the team \"([^\"]*)\"$")
    public void theStagedUserIsAddedToTheTeam(String teamName) throws Throwable {
        teamConfigurationService.addUserToTeam(teamName,this.user);
    }
}
