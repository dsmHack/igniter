package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubIntegrationService implements IntegrationService {
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getIntegrationServiceName() {
        return "gitHub";
    }

    @Override
    public void createTeam(String teamName) {

    }





}
