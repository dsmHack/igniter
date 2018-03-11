package com.dsmhack.igniter.configuration;

import com.dsmhack.igniter.services.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Configuration
public class IntegrationConfiguration {

    @Value("${dsmhack.integration.enabled.services}")
    private String[] driveIntegration;

    @Autowired
    private List<IntegrationService> availableServices;

    public List<IntegrationService> getActiveIntegrationServices(){
        throw new NotImplementedException();
    }


}
