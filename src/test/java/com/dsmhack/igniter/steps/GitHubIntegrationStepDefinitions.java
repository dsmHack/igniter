package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.SpringContextConfiguration;
import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import cucumber.api.PendingException;
import cucumber.api.java.bs.A;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class GitHubIntegrationStepDefinitions  {

    @Autowired
    IntegrationServicesConfiguration integrationServicesConfiguration;


    @Then("^The gitGub repo of \"([^\"]*)\" exists$")
    public void theGitGubRepoOfExists(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
