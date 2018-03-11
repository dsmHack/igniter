Feature: Application Integration services are configured at runtime

  Scenario Template: Only the one integration is enabled
    When The integration service "<IntegrationServiceName>" is enabled
    Then The active integration services contain "<IntegrationServiceName>"

    Examples:
      | IntegrationServiceName |
      | googleDrive            |
      | gitHub                 |
      | slack                  |