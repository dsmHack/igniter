package com.dsmhack.igniter.services.github;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.dsmhack.igniter.services.exceptions.IntegrationServiceException;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.kohsuke.github.*;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

public class GitHubIntegrationService implements IntegrationService {

  private final GitHubProperties gitHubProperties;

  private GitHubClient gitHubClient;
  private GitHub gitHubService;
  private GHOrganization organization;

  public GitHubIntegrationService(GitHubProperties gitHubProperties) {
    this.gitHubProperties = gitHubProperties;
  }

  @Override
  public String getIntegrationName() {
    return "github";
  }

  @Override
  public void createTeam(String teamName) throws DataConfigurationException {
    try {
      GHRepository ghRepository = buildOrGetRepository(teamName);
      GHTeam team = buildOrGetTeam(teamName, ghRepository);
      team.add(ghRepository, GHOrganization.Permission.PUSH);
    } catch (IOException e) {
      throw new DataConfigurationException(String.format("Error creating team '%s'.", teamName), e);
    }
  }

  @Override
  public TeamValidation validateTeam(String team) {
    return null;
  }

  private boolean isUserMember(String teamName, User user) throws DataConfigurationException {
    GHTeam teamByName = getTeamByName(teamName);
    return getUserForTeam(user, teamByName).isPresent();
  }

  private Optional<GHUser> getUserForTeam(User user, GHTeam team) throws DataConfigurationException {
    try {
      return team.getMembers().stream()
          .filter(u -> u.getLogin().equals(user.getGithubUsername()))
          .findFirst();
    } catch (IOException e) {
      throw new DataConfigurationException(
          String.format("Error fetching user '%s' from team '%s'", user.getGithubUsername(), team.getName())
      );
    }
  }

  @Override
  public void addUserToTeam(String teamName,
                            User user) throws IntegrationServiceException {
    try {
      if (!StringUtils.isEmpty(user.getGithubUsername())) {
        if (isUserMember(teamName, user)) {
          throw new ActionNotRequiredException(
              String.format("User '%s' is already on the team %s", user.getGithubUsername(), teamName)
          );
        }
        GHUser ghUser = getUser(user.getGithubUsername());
        GHTeam teamByName = getTeamByName(teamName);
        teamByName.add(ghUser, GHTeam.Role.MEMBER);
      }
    } catch (IOException e) {
      throw new DataConfigurationException(
          String.format("Error adding user '%s' to team '%s'.", user.getGithubUsername(), teamName), e
      );
    }
  }

  @Override
  public void removeUserFromTeam(String teamName, User user) throws IntegrationServiceException {
    try {
      GHTeam team = getTeamByName(teamName);

      GHUser userForTeam = getUserForTeam(user, team).orElseThrow(
          () -> new ActionNotRequiredException(
              String.format("User '%s' is already not part of the team '%s'", user.getGithubUsername(), teamName)
          )
      );
      team.remove(userForTeam);
    } catch (IOException e) {
      throw new DataConfigurationException(
          String.format("Error removing user '%s' from team '%s'.", user.getGithubUsername(), teamName), e
      );
    }
  }

  private GHTeam buildOrGetTeam(String teamName, GHRepository ghRepository) throws DataConfigurationException {
    Optional<GHTeam> optionalTeam = getTeamIfExists(teamName);
    if (!optionalTeam.isPresent()) {
      createTeam(gitHubProperties.getOrgName(), teamName, Collections.singletonList(ghRepository.getFullName()));
      optionalTeam = getTeamIfExists(teamName);
    }
    return optionalTeam.orElse(null);
  }

  private GHTeam getTeamByName(String teamName) throws DataConfigurationException {
    return getTeamIfExists(teamName)
        .orElseThrow(
            () -> new DataConfigurationException(String.format("Tried to work with non-existent team %s", teamName))
        );
  }

  private Optional<GHTeam> getTeamIfExists(String teamName) throws DataConfigurationException {
    try {
      return Optional.ofNullable(organization.getTeamByName(teamName));
    } catch (IOException e) {
      throw new DataConfigurationException(String.format("Error fetching team '%s'.", teamName), e);
    }
  }

  private GHUser getUser(String login) throws DataConfigurationException {
    return getUserIfExists(login)
        .orElseThrow(
            () -> new DataConfigurationException(String.format("Error fetching user '%s'.", login))
        );
  }

  private Optional<GHUser> getUserIfExists(String login) throws DataConfigurationException {
    try {
      return Optional.ofNullable(gitHubService.getUser(login));
    } catch (IOException e) {
      throw new DataConfigurationException(String.format("Error fetching user '%s'.", login), e);
    }
  }

  private Team createTeam(String organization,
                          String teamName,
                          List<String> repoNames) throws DataConfigurationException {
    try {
      Team team = new Team();
      team.setPermission("admin");
      team.setName(teamName);
      Map<String, Object> params = new HashMap<>();
      params.put("name", team.getName());
      params.put("permission", team.getPermission());
      params.put("privacy", "closed");
      if (repoNames != null) {
        params.put("repo_names", repoNames);
      }
      params.put("maintainers", Collections.singletonList(gitHubService.getMyself().getLogin()));

      String uri = "/orgs/" + organization + "/teams";
      return (Team) gitHubClient.post(uri, params, Team.class);
    } catch (IOException e) {
      throw new DataConfigurationException(String.format("Error creating team '%s'.", teamName), e);
    }
  }

  private GHRepository buildOrGetRepository(String teamName) throws DataConfigurationException {
    GHRepository repository = getRepositoryIfExists(teamName);
    if (repository == null) {
      repository = createRepository(teamName);
    }
    return repository;
  }

  private GHRepository getRepositoryIfExists(String teamName) throws DataConfigurationException {
    try {
      return organization.getRepository(teamName);
    } catch (IOException e) {
      throw new DataConfigurationException("Error getting repository '%s'.", e);
    }
  }

  private GHRepository createRepository(String teamName) throws DataConfigurationException {
    try {
      return organization.createRepository(teamName)
          .description("This is the repo for the dsmHack event for the team: " + teamName + "")
          .create();
    } catch (IOException e) {
      throw new DataConfigurationException(String.format("Error creating repository '%s'.", teamName), e);
    }
  }

  @PostConstruct
  public void configure() throws IOException {
    gitHubService = new GitHubBuilder()
        .withOAuthToken(this.gitHubProperties.getOAuthKey(), this.gitHubProperties.getOrgName())
        .build();
    organization = gitHubService.getOrganization(this.gitHubProperties.getOrgName());
    gitHubClient = new GitHubClient();
    gitHubClient.setOAuth2Token(this.gitHubProperties.getOAuthKey());
  }

}
