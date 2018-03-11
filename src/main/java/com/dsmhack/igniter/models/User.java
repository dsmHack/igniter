package com.dsmhack.igniter.models;

import java.util.Objects;

public class User {
    private String lastName;
    private String firstName;
    private String email;
    private String githubUsername;


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getGithubUsername(), user.getGithubUsername());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLastName(), getFirstName(), getEmail(), getGithubUsername());
    }
}
