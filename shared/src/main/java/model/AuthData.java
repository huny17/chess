package model;

import java.util.Objects;

public record AuthData(String token, String name){

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthData authData = (AuthData) o;
        return Objects.equals(token, authData.token) && Objects.equals(name, authData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, name);
    }

    @Override
    public String toString() {
        return String.format("token: %s, name: %s", token, name);
    }
}
