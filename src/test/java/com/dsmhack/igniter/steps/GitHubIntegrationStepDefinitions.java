package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.IgniterProperties;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class GitHubIntegrationStepDefinitions  {

    @Autowired
    IgniterProperties igniterProperties;


    @Then("^The gitGub repo of \"([^\"]*)\" exists$")
    public void theGitGubRepoOfExists(String teamName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }
}
