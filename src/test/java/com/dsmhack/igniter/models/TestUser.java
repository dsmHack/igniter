package com.dsmhack.igniter.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUser {

  @Test
  public void testUserImportService_constructor() {
    String firstName = "aFirstName";
    String lastName = "aLastName";
    String email = "aEmail";
    String githubUsername = "aGithubUser";
    User actualUser = User.builder()
        .firstName(firstName)
        .lastName(lastName)
        .slackEmail(email)
        .githubUsername(githubUsername)
        .build();

    assertEquals(actualUser.getFirstName(), firstName);
    assertEquals(actualUser.getLastName(), lastName);
    assertEquals(actualUser.getSlackEmail(), email);
    assertEquals(actualUser.getGithubUsername(), githubUsername);
  }

}
