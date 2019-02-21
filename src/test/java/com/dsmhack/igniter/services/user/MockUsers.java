package com.dsmhack.igniter.services.user;

import com.dsmhack.igniter.models.User;

import java.util.ArrayList;
import java.util.List;

public final class MockUsers {

  public static List<User> getUsers() {
    List<User> users = new ArrayList<>();
    users.add(
        User.builder()
            .firstName("Tony")
            .lastName("Stark")
            .slackEmail("tony@starkindustries.com")
            .githubUsername("ironman")
            .build()
    );
    users.add(
        User.builder()
            .firstName("Steve")
            .lastName("Rogers")
            .slackEmail("steve.rogers@hotmail.com")
            .githubUsername("captain")
            .build()
    );
    users.add(
        User.builder()
            .firstName("Bruce")
            .lastName("Banner")
            .slackEmail("bbanner@gmail.com")
            .githubUsername("hulk")
            .build()
    );
    return users;
  }

}
