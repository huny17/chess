package model;

import java.util.Objects;

public record AuthData(String username, String authToken){

    @Override
    public String toString() {
        return String.format("token: %s, name: %s",username, authToken);
    }
}
