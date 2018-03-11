package com.dsmhack.igniter.configuration;

import com.dsmhack.igniter.services.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class IntegrationServicesConfiguration {

    @Value("#{'${dsmhack.integration.enabled.services}'.split(',')}")
    private List<String> activeIntegrations;

    private final List<IntegrationService> availableServices;

    @Autowired
    public IntegrationServicesConfiguration(List<IntegrationService> availableServices) {
        this.availableServices = availableServices;
    }

    public List<IntegrationService> getActiveIntegrationServices(){
        return this.availableServices.stream()
                .filter(integrationService -> activeIntegrations.contains(integrationService.getIntegrationServiceName()))
                .collect(Collectors.toList());
    }

    public void activateIntegrationService(String integrationServiceName) {
         if(!activeIntegrations.contains(integrationServiceName)){
             activeIntegrations.add(integrationServiceName);
         }
    }


}
