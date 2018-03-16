Feature: A new team is created in slack

  Background:
    Given The Slack integration is enabled

  Scenario: Admin adds a new team that has not previously been created
    When The Admin creates the team "Team_1"
    Then The Slack Channel of "Team_1" exists

  Scenario: Admin adds a new user to a team
    Given The following user is created
      | lastName | firstName | email   | githubUsername  |
      | doe      | john      | anEmail | aGithubUserName |
    And The integration service "slack" is enabled
    And The Slack Channel of "Team_1" exists
    When The Admin adds the user with the email of "anEmail" to the team "Team_1"
    Then the user with the email of "anEmail" should be present in the slack channel "Team_1"