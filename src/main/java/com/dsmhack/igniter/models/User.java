package com.dsmhack.igniter.models;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private String lastName;
    private String firstName;
    private String email;
    private String githubUsername;
    private List<Team> teams;

    public User(String firstName, String lastName, String email, String githubUsername) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.githubUsername = githubUsername;
    }
}
