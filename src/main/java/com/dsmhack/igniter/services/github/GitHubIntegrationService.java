package com.dsmhack.igniter.services.github;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubIntegrationService implements IntegrationService {

    private final IntegrationServicesConfiguration integrationServicesConfiguration;

    final ObjectMapper objectMapper;
    private GitHubClient gitHubClient;
    private GitHubConfig gitHubConfig;
    private GitHub gitHubService;
    private GHOrganization organization;


    @Autowired
    public GitHubIntegrationService(ObjectMapper objectMapper, IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.objectMapper = objectMapper;
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }

    @Override
    public String getIntegrationServiceName() {
        return "gitHub";
    }

    @Override
    public void createTeam(String teamName) {
        GHRepository ghRepository;
        GHTeam team;
        try {
            ghRepository = buildOrGetRepository(teamName);
            team = buildOrGetTeam(teamName, ghRepository);
            team.add(ghRepository, GHOrganization.Permission.PUSH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }

    @Override
    public void addUserToTeam(String teamName, User user) {
        GHUser ghUser = null;
        try {
            ghUser = gitHubService.getUser(user.getGithubUsername());
            organization.getTeamByName(teamName).add(ghUser, GHTeam.Role.MEMBER);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private GHTeam buildOrGetTeam(String teamName, GHRepository ghRepository) throws IOException {
        GHTeam team = getTeam(teamName);
        if (team == null) {
            createTeam(gitHubConfig.getOrgName(), teamName, Collections.singletonList(ghRepository.getFullName()));
            team = getTeam(teamName);
        }
        return team;
    }

    private GHTeam getTeam(String teamName) throws IOException {
        return organization.getTeamByName(teamName);
    }

    private Team createTeam(String organization, String teamName, List<String> repoNames) throws IOException {
        Team team = new Team();
        team.setPermission("admin");
        team.setName(teamName);
        StringBuilder uri = new StringBuilder("/orgs");
        uri.append('/').append(organization);
        uri.append("/teams");
        Map<String, Object> params = new HashMap();
        params.put("name", team.getName());
        params.put("permission", team.getPermission());
        params.put("privacy", "closed");
        if (repoNames != null) {
            params.put("repo_names", repoNames);
        }
        params.put("maintainers",Collections.singletonList(gitHubService.getMyself().getLogin()));
        return (Team) gitHubClient.post(uri.toString(), params, Team.class);
    }





    private GHRepository buildOrGetRepository(String teamName) throws IOException {
        GHRepository repository = getRepositoryIfExists(teamName);
        if (repository == null) {
            repository = organization.createRepository(teamName).description("This is the repo for the '" + gitHubConfig.getPrefix() + "' event for the team:'" + teamName + "'").create();
        }
        return repository;
    }

    private GHRepository getRepositoryIfExists(String teamName) throws IOException {
        return organization.getRepository(teamName);
    }

    @PostConstruct
    public void configure() throws IOException {
        this.gitHubConfig = integrationServicesConfiguration.getKeyContent("git-hub-credentials.json",GitHubConfig.class);
        gitHubService = new GitHubBuilder().withOAuthToken(this.gitHubConfig.getOAuthKey(), this.gitHubConfig.getOrgName()).build();
        organization = gitHubService.getOrganization(this.gitHubConfig.getOrgName());
        gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(this.gitHubConfig.getOAuthKey());
    }

}
