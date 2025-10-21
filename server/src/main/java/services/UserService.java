package services;

import dataaccess.AuthDAO;
import dataaccess.GeneralException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import model.request.LoginRequest;
import model.request.LogoutRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.LogoutResult;
import model.result.RegisterResult;

import java.util.HashMap;
import java.util.UUID;

public class UserService {

    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    public UserService(UserDAO userDataAccess, AuthDAO authDataAccess){
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public RegisterResult register(RegisterRequest req) throws GeneralException{
        if(userDataAccess.getUser(req.username()) != null){
            throw new GeneralException("403","Username already taken");
        }

        UserData user = new UserData(req.username(), req.password(), req.email());
        userDataAccess.createUser(user);

        AuthData auth = new AuthData(user.username(), generateAuthToken());
        authDataAccess.createAuth(auth);

        return new RegisterResult(auth.username(), auth.authToken());
    }

    public LoginResult login(LoginRequest req) throws GeneralException{

        if(req.username() == null | req.password() == null){
            throw new GeneralException("400","Username and password required");
        }
        if(!userDataAccess.getUserMap().containsKey(req.username())){ //no user obtained
            throw new GeneralException("401","User does not exist");
        }
        if(!userDataAccess.getUser(req.username()).password().equals(req.password())) {
            throw new GeneralException("401", "Wrong password");
        }

        AuthData auth = new AuthData(req.username(), generateAuthToken());
        authDataAccess.createAuth(auth);

        return new LoginResult(auth.username(), auth.authToken());
    }

    public LogoutResult logout(LogoutRequest req){
        authDataAccess.deleteAuth(req.authToken());

        return new LogoutResult("");
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
