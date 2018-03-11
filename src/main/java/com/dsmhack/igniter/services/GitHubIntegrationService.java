package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;

@Service
public class GitHubIntegrationService implements IntegrationService {
    @Override
    public String getIntegrationServiceName() {
        return "gitHub";
    }
}
