package com.dsmhack.igniter.configuration;

import com.dsmhack.igniter.services.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class IntegrationServicesConfiguration {

    @Value("${dsmhack.integration.enabled.services}")
    private List<String> driveIntegration;

    private final List<IntegrationService> availableServices;

    @Autowired
    public IntegrationServicesConfiguration(List<IntegrationService> availableServices) {
        this.availableServices = availableServices;
    }

    public List<IntegrationService> getActiveIntegrationServices(){
        return this.availableServices.stream().filter(integrationService -> CollectionUtils.arrayToList(driveIntegration).contains(integrationService.getIntegrationServiceName())).collect(Collectors.toList());
    }

    public void activateIntegrationService(String integrationServiceName) {
         if(availableServices.stream().noneMatch(integrationService -> integrationService.getIntegrationServiceName().equals(integrationServiceName))){
             driveIntegration.add(integrationServiceName);
         }
    }
}
