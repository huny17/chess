package model;

import java.util.Objects;

public record AuthData(String token, String name){

    @Override
    public String toString() {
        return String.format("token: %s, name: %s", token, name);
    }
}
