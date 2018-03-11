Feature: Application Integration services are configured at runtime

  Scenario Template: Only the one integration is enabled
    When The integration service "<IntegrationServiceName>" is enabled
    Then The only active integration service is "<IntegrationServiceName>"

    Examples:
      | IntegrationServiceName |
      | googleDrive            |
      | gitHub                 |
      | slack                  |