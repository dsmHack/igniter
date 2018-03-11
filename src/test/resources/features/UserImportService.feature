Feature: A config file is loaded and data is supplied
  Background:
    Given a user.csv file is present



  Scenario: A service needs config data
    When the service is started it loads the default config file of "user.csv"
