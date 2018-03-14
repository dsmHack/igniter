package com.dsmhack.igniter.configuration;

import com.dsmhack.igniter.services.IntegrationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Getter
@Setter
public class IntegrationServicesConfiguration {

    @Value("#{'${dsmhack.integration.enabled.services}'.split(',')}")
    private List<String> activeIntegrations;

    @Value("${keys.base.path:./}")
    private String keyPath;

    @Value("${team.prefix:''}")
    String teamPrefix;

    public String getCompositeName(String teamName) {
        return getTeamPrefix() + teamName;
    }

}
