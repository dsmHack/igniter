package com.dsmhack.igniter.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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

    @Value("${team.number.of.teams}")
    Integer teamNumber;

    final  ObjectMapper objectMapper;

    @Autowired
    public IntegrationServicesConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getCompositeName(String teamName) {
        return getTeamPrefix() + teamName;
    }

    public <T> T getKeyContent(String filePath, Class<T> clazz) throws IOException {
        File file = Paths.get(getKeyPath(), filePath).toFile();
        return objectMapper.readerFor(clazz).readValue(file);
    }
}
