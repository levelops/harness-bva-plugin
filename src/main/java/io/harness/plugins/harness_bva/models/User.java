package io.harness.plugins.harness_bva.models;

import com.google.common.base.Objects;

public class User {
    private final String userName;
    private final String fullName;
    private final String email;

    public User(String userName, String fullName, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(userName, user.userName) &&
                Objects.equal(fullName, user.fullName) &&
                Objects.equal(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName, fullName, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}