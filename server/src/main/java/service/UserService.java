package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.LogoutResult;
import model.result.RegisterResult;
import exceptions.*;

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
            throw new GeneralException(GeneralException.ExceptionType.forbidden,"Username already taken");
        }
        if(req.password() == null | req.email() == null){
            throw new GeneralException(GeneralException.ExceptionType.invalid,"Password or Email is empty");
        }

        UserData user = new UserData(req.username(), req.password(), req.email());
        userDataAccess.createUser(user);

        AuthData auth = new AuthData(user.username(), generateAuthToken());
        authDataAccess.createAuth(auth);

        return new RegisterResult(auth.username(), auth.authToken());
    }

    public LoginResult login(LoginRequest req) throws GeneralException{

        if(req.username() == null | req.password() == null){
            throw new GeneralException(GeneralException.ExceptionType.invalid,"Username and password required");
        }
        if(userDataAccess.getUser(req.username()) == null){//Map().containsKey(req.username())){ //no user obtained
            throw new GeneralException(GeneralException.ExceptionType.unauthorized,"User does not exist");
        }
        if(!userDataAccess.verifyUser(req.username(), req.password())) {
            throw new GeneralException(GeneralException.ExceptionType.unauthorized, "Wrong password");
        }

        AuthData auth = new AuthData(req.username(), generateAuthToken());
        authDataAccess.createAuth(auth);

        return new LoginResult(auth.username(), auth.authToken());
    }

    public LogoutResult logout(String authToken) throws GeneralException{
        authDataAccess.deleteAuth(authToken);
        return new LogoutResult(" ");
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }

}
