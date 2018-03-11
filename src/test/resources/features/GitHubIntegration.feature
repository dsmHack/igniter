Feature: A new team is created in github

  Scenario: Admin adds a new team that has not previously been created
    Given The git hub oauth key is configured
    And The GitHub integration is enabled
    When The Admin creates the team "Team 1"
    Then The google Drive folder of "Team 1" exists