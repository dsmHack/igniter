package com.dsmhack.igniter.services.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitHubProperties {
  private String oAuthKey;
  private String orgName;
}
