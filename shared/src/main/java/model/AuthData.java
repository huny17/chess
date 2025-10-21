package model;

import java.util.Objects;

public record AuthData(String username, String authToken){

    @Override
    public String toString() {
        return String.format("name: %s, token: %s",username, authToken);
    }
}
