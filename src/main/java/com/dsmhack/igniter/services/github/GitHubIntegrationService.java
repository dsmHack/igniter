package com.dsmhack.igniter.services.github;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.services.IntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.TeamService;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.kohsuke.github.GHOrganization.Permission.PUSH;

@Service
public class GitHubIntegrationService implements IntegrationService {

    private final IntegrationServicesConfiguration integrationServicesConfiguration;

    final ObjectMapper objectMapper;
    private GitHubClient gitHubClient;
    private GitHubConfig gitHubConfig;
    private TeamService teamService;
    private OrganizationService organizationService;
    private boolean configured = false;
    private RepositoryService repositoryService;
    private User orgUser;
    private GitHub gitHubService;


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
        GHCreateRepositoryBuilder repository = null;
        GHRepository ghRepository = null;
        GHTeam team;
        String team1;
        try {
            ghRepository = buildOrGetRepository(teamName);
            team = buildOrGetTeam(teamName, ghRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



   @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }

    private GHTeam buildOrGetTeam(String teamName, GHRepository ghRepository) throws IOException {
        GHOrganization organization = gitHubService.getOrganization(gitHubConfig.getOrgName());

        GHTeam team = organization.getTeamByName(teamName);

        if (team != null) {
            GHTeam matchingTeam = ghRepository.getTeams().stream().filter(t -> t.getId() == team.getId()).findFirst().orElse(null);
            if(matchingTeam!=null){
                if(!PUSH.equals (GHOrganization.Permission.valueOf(matchingTeam.getPermission()))){
                    matchingTeam.add(ghRepository, PUSH);
                }
              }
            return team;
        }

        return organization.createTeam(teamName, PUSH, ghRepository);
    }

//    private Team buildTeam(String teamName) throws IOException {
//        Team team = new Team();
//        team.setName(teamName);
//        team.setPermission("push");
//        return teamService.createTeam(gitHubConfig.getOrgName(), team);
//    }

    private GHRepository buildOrGetRepository(String teamName) throws IOException {
        GHRepository repository = getRepositoryIfExists(teamName);
        if (repository == null) {
            repository = gitHubService.createRepository(teamName).description("This is the repo for the '" + gitHubConfig.getPrefix() + "' event for the team:'" + teamName + "'").private_(false).create();
        }
        return repository;
    }

    private GHRepository getRepositoryIfExists(String teamName) throws IOException {
        return gitHubService.getMyself().getRepository(teamName);
    }

    @PostConstruct
    public void configure() throws IOException {
        if (this.configured)
            return;
        File file = Paths.get(integrationServicesConfiguration.getKeyPath(), "git-hub-credentials.json").toFile();
        gitHubConfig= objectMapper.readerFor(GitHubConfig.class).readValue(file);
        gitHubService = new GitHubBuilder().withOAuthToken(gitHubConfig.getOAuthKey(), gitHubConfig.getOrgName()).build();
        this.configured = true;
    }

    private class GitHubAuth implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
             if(headers.get("Authorization")==null){
                 headers.add("Authorization","token "+gitHubConfig.getOAuthKey());
             }

            return execution.execute(request,body);
        }
    }

}
