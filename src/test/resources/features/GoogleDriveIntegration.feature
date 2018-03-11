Feature: A new team is created in google drive

  Background:
    Given The integration service "googleDrive" is enabled
    And The integration service googleDrive is configured

  Scenario: Admin creates a new team that has not previously been created
    When The Admin creates the team "Team_1"
    Then The google Drive folder of "Team_1" exists

  Scenario: Admin creates a new user
    Given The Admin creates the team "Team_1"
    And The following user is created
      | FirstName | LastName | ContactEmail       | GoogleEmail        |
      | Josh      | Anderson | Inc@kickercost.com | Inc@kickercost.com |
    When the Admin assigns the user to "Team_1"
    Then The google drive folder for "Team_1" should have the user "Inc@kickercost.com" added with read rights

  Scenario: Admin creates a user that already exists for a team
    Given The Admin creates the team "Team_1"
    And The following user is created
      | FirstName | LastName | ContactEmail       | GoogleEmail        |
      | Josh      | Anderson | Inc@kickercost.com | Inc@kickercost.com |
    When the Admin assigns the user to "Team_1"
    Then The google drive folder for "Team_1" should have the user "Inc@kickercost.com" added with read rights
    Then The following user is created
      | FirstName | LastName | ContactEmail       | GoogleEmail        |
      | Josh      | Anderson | Inc@kickercost.com | Inc@kickercost.com |
    Then The google drive folder for "Team_1" should have the user "Inc@kickercost.com" added with read rights

  Scenario: Admin creates a team that already exists
      Given The Admin creates the team "Team_1"
      And The google Drive folder of "Team_1" exists
      And The admin created a "Team_1/sampleFolder"
      And The google Drive folder of "Team_1/sampleFolder" exists
      When The Admin creates the team "Team_1"
      Then The google Drive folder of "Team_1/sampleFolder" exists

      