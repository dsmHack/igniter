package com.dsmhack.igniter.services.github;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

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
    public TeamValidation validateTeam(String team) {
        return null;
    }

    private GHTeam getTeamByName(String teamName) throws IOException, DataConfigurationException {
        GHTeam teamByName = organization.getTeamByName(teamName);
        if (teamByName == null)
            throw new DataConfigurationException(String.format("Tried to work with non-existent team %s", teamName));
        return teamByName;
    }


    private boolean isUserMember(String teamName, User user) throws DataConfigurationException, IOException {
        GHTeam teamByName = null;
        try {
            teamByName = getTeamByName(teamName);
        } catch (Exception e) {
            throw new DataConfigurationException(String.format("Error in fetching team %s while checking for user '%s' membership ", user.getGithubUsername(), teamName), e);
        }
        return getUserForTeam(user, teamByName) != null;
    }

    private GHUser getUserForTeam(User user, GHTeam team) throws IOException {
        return team.getMembers().stream().filter(u -> u.getLogin().equals(user.getGithubUsername())).findFirst().orElse(null);
    }


    @Override
    public void addUserToTeam(String teamName, User user) throws ActionNotRequiredException, DataConfigurationException, IOException {
        if (isUserMember(teamName, user))
            throw new ActionNotRequiredException(String.format("User '%s' is already on the team %s", user.getGithubUsername(), teamName));
        GHUser ghUser=gitHubService.getUser(user.getGithubUsername());
        GHTeam teamByName = organization.getTeamByName(teamName);
        teamByName.add(ghUser, GHTeam.Role.MEMBER);
    }

    @Override
    public void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException {
        if(!isUserMember(teamName,user)){
            throw new ActionNotRequiredException(String.format("User '%s' is already not part of the team '%s'",user.getGithubUsername(),teamName ));
        }
        GHTeam team = getTeam(teamName);
        team.remove(getUserForTeam(user,team));
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
        params.put("maintainers", Collections.singletonList(gitHubService.getMyself().getLogin()));
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
        this.gitHubConfig = integrationServicesConfiguration.getKeyContent("git-hub-credentials.json", GitHubConfig.class);
        gitHubService = new GitHubBuilder().withOAuthToken(this.gitHubConfig.getOAuthKey(), this.gitHubConfig.getOrgName()).build();
        organization = gitHubService.getOrganization(this.gitHubConfig.getOrgName());
        gitHubClient = new GitHubClient();
        gitHubClient.setOAuth2Token(this.gitHubConfig.getOAuthKey());
    }

}
