package com.dsmhack.igniter.services.slack;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlackConfig {
    private String clientId;
    private String clientSecret;
    private String oAuthKey;
}
