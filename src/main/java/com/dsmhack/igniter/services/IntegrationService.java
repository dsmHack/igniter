package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;

import java.io.IOException;
import java.util.Map;

public interface IntegrationService {
    String getIntegrationServiceName();

    void createTeam(String teamName);
    Map<String,String> validateTeamName(String team);

    void addUserToTeam(String teamName, User user) throws ActionNotRequiredException, DataConfigurationException, IOException;

    void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException;
}
