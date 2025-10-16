package services;

import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {

    private UserDAO dataAccess;

    public UserService(UserDAO dataAccess){
        this.dataAccess = dataAccess;
    }

    public void clear(){
        dataAccess.clear();
    }

    public AuthData register(UserData user) throws Exception{
        if(dataAccess.getUser(user.username()) != null){
            throw new Exception("username already taken");
        }
        dataAccess.createUser(user);
        var authData = new AuthData(user.username(), generateAuthToken());
        return authData;
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
