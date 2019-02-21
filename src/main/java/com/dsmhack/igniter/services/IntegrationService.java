package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;

import java.io.IOException;

public interface IntegrationService {
    String getIntegrationName();

    void createTeam(String teamName) throws DataConfigurationException, ActionNotRequiredException;
    TeamValidation validateTeam(String team) throws DataConfigurationException;

    void addUserToTeam(String teamName, User user) throws ActionNotRequiredException, DataConfigurationException, IOException;

    void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException;
}
