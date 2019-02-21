package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.IntegrationServiceException;

public interface IntegrationService {
    String getIntegrationName();

    void createTeam(String teamName) throws IntegrationServiceException;

    TeamValidation validateTeam(String team) throws IntegrationServiceException;

    void addUserToTeam(String teamName, User user) throws IntegrationServiceException;

    void removeUserFromTeam(String teamName, User user) throws IntegrationServiceException;
}
