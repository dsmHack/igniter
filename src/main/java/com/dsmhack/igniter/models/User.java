package com.dsmhack.igniter.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {
    private final String lastName;
    private final String firstName;
    private final String slackEmail;
    private final String githubUsername;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {

    }
}
