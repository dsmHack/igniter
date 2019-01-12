package com.dsmhack.igniter.services.gdrive;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleDriveIntegrationService implements IntegrationService {
    public void createTeam(String teamName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TeamValidation validateTeam(String team) {
        return null;
    }

    @Override
    public void addUserToTeam(String compositeName, User user) {

    }

    @Override
    public void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException {

    }

    @Override
    public String getIntegrationServiceName() {
        return "gdrive";
    }
}
