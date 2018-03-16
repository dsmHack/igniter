package com.dsmhack.igniter.services.slack;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.dsmhack.igniter.services.github.GitHubConfig;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.impl.MethodsClientImpl;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
public class SlackIntegrationService implements IntegrationService {
    private final IntegrationServicesConfiguration integrationServicesConfiguration;
    private SlackConfig slackConfig;

    @Autowired
    public SlackIntegrationService(IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }

    @Override
    public String getIntegrationServiceName() {
        return "slack";
    }

    @Override
    public void createTeam(String teamName) {

    }

    @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }



    @Override
    public void addUserToTeam(String compositeName, User user) {

    }

    @Override
    public void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException {

    }


    @PostConstruct
    public void configure() throws IOException {
        this.slackConfig = integrationServicesConfiguration.getKeyContent("slack-credentials.json", SlackConfig.class);
    }

}
