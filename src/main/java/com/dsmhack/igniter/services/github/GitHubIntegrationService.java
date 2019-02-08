package com.dsmhack.igniter.services.github;

import com.dsmhack.igniter.IgniterProperties;
import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
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

  private final IgniterProperties igniterProperties;
  private final GitHubProperties gitHubProperties;

  private GitHubClient gitHubClient;
  private GitHub gitHubService;
  private GHOrganization organization;

  @Autowired
  public GitHubIntegrationService(IgniterProperties igniterProperties,
                                  GitHubProperties gitHubProperties) {
    this.igniterProperties = igniterProperties;
    this.gitHubProperties = gitHubProperties;
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
    if (teamByName == null) {
      throw new DataConfigurationException(String.format("Tried to work with non-existent team %s", teamName));
    }
    return teamByName;
  }


  private boolean isUserMember(String teamName, User user) throws DataConfigurationException, IOException {
    GHTeam teamByName = null;
    try {
      teamByName = getTeamByName(teamName);
    } catch (Exception e) {
      throw new DataConfigurationException(String.format(
          "Error in fetching team %s while checking for user '%s' membership ",
          user.getGithubUsername(),
          teamName), e);
    }
    return getUserForTeam(user, teamByName) != null;
  }

  private GHUser getUserForTeam(User user, GHTeam team) throws IOException {
    return team.getMembers().stream()
        .filter(u -> u.getLogin().equals(user.getGithubUsername()))
        .findFirst()
        .orElse(null);
  }

  @Override
  public void addUserToTeam(String teamName,
                            User user) throws ActionNotRequiredException, DataConfigurationException, IOException {
    if (isUserMember(teamName, user)) {
      throw new ActionNotRequiredException(String.format("User '%s' is already on the team %s",
                                                         user.getGithubUsername(),
                                                         teamName));
    }
    GHUser ghUser = gitHubService.getUser(user.getGithubUsername());
    GHTeam teamByName = organization.getTeamByName(teamName);
    teamByName.add(ghUser, GHTeam.Role.MEMBER);
  }

  @Override
  public void removeUserFromTeam(String teamName,
                                 User user) throws IOException, DataConfigurationException, ActionNotRequiredException {
    if (!isUserMember(teamName, user)) {
      throw new ActionNotRequiredException(String.format("User '%s' is already not part of the team '%s'",
                                                         user.getGithubUsername(),
                                                         teamName));
    }
    GHTeam team = getTeam(teamName);
    team.remove(getUserForTeam(user, team));
  }

  private GHTeam buildOrGetTeam(String teamName, GHRepository ghRepository) throws IOException {
    GHTeam team = getTeam(teamName);
    if (team == null) {
      createTeam(gitHubProperties.getOrgName(), teamName, Collections.singletonList(ghRepository.getFullName()));
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
      repository = organization.createRepository(teamName)
          .description("This is the repo for the '" + gitHubProperties.getPrefix() + "' event for the team:'" + teamName + "'")
          .create();
    }
    return repository;
  }

  private GHRepository getRepositoryIfExists(String teamName) throws IOException {
    return organization.getRepository(teamName);
  }

  @PostConstruct
  public void configure() throws IOException {
    if (igniterProperties.isActiveIntegration(this.getIntegrationServiceName())) {
      gitHubService = new GitHubBuilder()
          .withOAuthToken(this.gitHubProperties.getOAuthKey(), this.gitHubProperties.getOrgName())
          .build();
      organization = gitHubService.getOrganization(this.gitHubProperties.getOrgName());
      gitHubClient = new GitHubClient();
      gitHubClient.setOAuth2Token(this.gitHubProperties.getOAuthKey());
    }
  }

}
