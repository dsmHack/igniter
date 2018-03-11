Feature: Application Integration services are configured at runtime

  Scenario Template: Only the one integration is enabled
    Given The integration service "<IntegrationServiceName>" is enabled
    When The active integrations are checked
    Then The only active integration service is "<IntegrationServiceName>"

    Examples:
      | IntegrationServiceName |
      | googleDrive            |
      | gitHub                 |
      | slack                  |