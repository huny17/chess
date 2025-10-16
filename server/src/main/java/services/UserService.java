package services;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {

    private UserDAO userDataAccess;
    private AuthDAO authDataAccess;

    public UserService(UserDAO userDataAccess, AuthDAO authDataAccess){
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    //public void clear(){
    //    userDataAccess.clear();
    //}

    public AuthData register(UserData user) throws Exception{
        if(userDataAccess.getUser(user.username()) != null){
            throw new Exception("username already taken");
        }
        userDataAccess.createUser(user);
        var authData = new AuthData(user.username(), generateAuthToken());
        return authData;
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
