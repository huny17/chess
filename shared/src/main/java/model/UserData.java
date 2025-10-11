package model;

public class UserData {

    private final String name;
    private final String password;
    private final String email;

    UserData(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
