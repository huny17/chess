package services;

import model.AuthData;
import model.RegistrationResult;
import model.UserData;

public class UserService {

    private DataAccess dataAccess;

    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public RegisterationResult register(UserData user){
        dataAccess.saveUser(user);
        return new RegistrationResult(new AuthData(user.username(), "token"));
    }
}
