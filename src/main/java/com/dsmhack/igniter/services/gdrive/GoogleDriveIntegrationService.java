package com.dsmhack.igniter.services.gdrive;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Map;

@Service
public class GoogleDriveIntegrationService implements IntegrationService {
    public void createTeam(String teamName) {
        throw new NotImplementedException();
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

    @Override
    public String getIntegrationServiceName() {
        return "gdrive";
    }
}
