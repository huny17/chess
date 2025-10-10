package model;

import java.util.Objects;

public class AuthData {
    private final String token;
    private final String name;

    AuthData(String token, String name){
        this.token = token;
        this.name = name;
    }

    public String getToken(){
        return token;
    }

    public String getName(){
        return name;
    }


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
