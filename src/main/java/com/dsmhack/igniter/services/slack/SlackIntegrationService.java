package com.dsmhack.igniter.services.slack;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SlackIntegrationService implements IntegrationService {
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
}
