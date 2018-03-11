package com.dsmhack.igniter.steps;

import com.dsmhack.igniter.SpringContextConfiguration;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class SlackIntegrationStepDefinitions  {
    @Given("^The Slack api key is configured$")
    public void theSlackApiKeyIsConfigured() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The Slack integration is enabled$")
    public void theSlackIntegrationIsEnabled() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^The Slack Channel of \"([^\"]*)\" exists$")
    public void theSlackChannelOfExists(String channelName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
