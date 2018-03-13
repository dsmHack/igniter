package com.dsmhack.igniter.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubConfig {
    private String oAuthKey;
    private String clientId;
    private String secretKey;
    private String orgName;
    private String prefix;

}
