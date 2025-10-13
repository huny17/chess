package model;

import java.util.Objects;

public record UserData(String username, String password, String email) {

    @Override
    public String toString() {
        return String.format("Name: %s, Password: %s, Email: %s,", username, password, email);
    }
}
