Feature: A config file is loaded and data is supplied

  Scenario: The service should load the default user.csv
    When the service is started it loads the default config file of "exampleUser.csv"

  Scenario: the list of users is returned
    Given The following users are created
      | lastName | firstName | email   | githubUsername  |
      | doe      | john      | anEmail | aGithubUserName |
    When the getUsers is called the following user exists
      | lastName | firstName | email   | githubUsername  |
      | doe      | john      | anEmail | aGithubUserName |


  Scenario: it should return the "exampleUser.csv" as a string
    When the getFileAsString is called with the following path "exampleUser.csv"
