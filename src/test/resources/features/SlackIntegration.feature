Feature: A new team is created in slack

  Scenario: Admin adds a new team that has not previously been created
    Given The Slack api key is configured
    And The Slack integration is enabled
    When The Admin creates the team "Team 1"
    Then The Slack Channel of "Team 1" exists

