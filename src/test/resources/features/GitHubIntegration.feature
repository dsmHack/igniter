Feature: A new team is created in github

  Scenario: Admin adds a new team that has not previously been created
    Given The integration service "gitHub" is enabled
    When The Admin creates the team "Team_1"
    Then The gitGub repo of "Team_1" exists
