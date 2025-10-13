package services;

import model.AuthData;
import model.RegistrationRecord;
import model.UserData;

public class UserService {

    public void register(UserData user){
        new RegistrationRecord(new AuthData(user.username(), "token"));
    }
}
