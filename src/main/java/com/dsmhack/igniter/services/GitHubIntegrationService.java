package com.dsmhack.igniter.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.TeamService;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.ftp.FtpDirEntry;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.kohsuke.github.GHOrganization.Permission.PUSH;

@Service
public class GitHubIntegrationService implements IntegrationService {
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
    public GitHubIntegrationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

        try {
            ghRepository = buildOrGetRepository(teamName);
            team = buildOrGetTeam(teamName, ghRepository);


        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private GHTeam buildOrGetTeam(String teamName, GHRepository ghRepository) throws IOException {
        GHOrganization organization = gitHubService.getOrganization(gitHubConfig.getOrgName());
        GHTeam team = organization.getTeamByName(getCompositeName(teamName));
        if (team != null) {
            GHTeam matchingTeam = ghRepository.getTeams().stream().filter(t -> t.getId() == team.getId()).findFirst().orElse(null);
            if(matchingTeam!=null){
                if(!PUSH.equals (GHOrganization.Permission.valueOf(matchingTeam.getPermission()))){
                    matchingTeam.add(ghRepository, PUSH);
                }
              }
            return team;
        }
        return organization.createTeam(getCompositeName(teamName), PUSH, ghRepository);
    }

    private Team buildTeam(String teamName) throws IOException {
        Team team = new Team();
        team.setName(getCompositeName(teamName));
        team.setPermission("push");
        return teamService.createTeam(gitHubConfig.getOrgName(), team);
    }

    private GHRepository buildOrGetRepository(String teamName) throws IOException {
        GHRepository repository = gitHubService.getMyself().getRepository(getCompositeName(teamName));
        if (repository == null) {
            repository = gitHubService.createRepository(getCompositeName(teamName)).description("This is the repo for the '" + gitHubConfig.getPrefix() + "' event for the team:'" + teamName + "'").private_(false).create();
        }
        return repository;
    }

    private String getCompositeName(String teamName) {
        return gitHubConfig.getPrefix() + "_" + teamName;
    }

    @PostConstruct
    public void configure() throws IOException {
        if (this.configured)
            return;

        gitHubConfig = objectMapper.readerFor(GitHubConfig.class).readValue(this.getClass().getClassLoader().getResource("keys/git-hub-credentials.json"));
        gitHubService = new GitHubBuilder().withOAuthToken(gitHubConfig.getOAuthKey(), gitHubConfig.getOrgName()).build();

        this.configured = true;

    }


}
