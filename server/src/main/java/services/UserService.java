package services;

import dataaccess.DataAccess;
import model.AuthData;
import model.RegistrationResult;
import model.UserData;

public class UserService {

    private DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public void clear(){
        dataAccess.clear();
    }

    public AuthData register(UserData user) throws Exception{
        if(dataAccess.getUser(user.username()) != null){
            throw new Exception();
        }
        dataAccess.createUser(user);
        var authData = new AuthData(user.username(), generateAuthToken());
        return authData;
    }

    private void generateAuthToken() {
    }
}
