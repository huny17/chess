package model;

import java.util.Objects;

public record UserData(String name, String password, String email) {

    @Override
    public String toString() {
        return String.format("Name: %s, Password: %s, Email: %s,", name, password, email);
    }
}
