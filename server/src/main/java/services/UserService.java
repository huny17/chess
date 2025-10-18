package services;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import model.request.LoginRequest;
import model.request.LogoutRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.LogoutResult;
import model.result.RegisterResult;
import org.eclipse.jetty.server.Authentication;

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

    public RegisterResult register(RegisterRequest req) throws Exception{
        if(userDataAccess.getUser(req.username()) != null){
            throw new Exception("username already taken");
        }

        UserData user = new UserData(req.username(), req.password(), req.email());
        userDataAccess.createUser(user);

        AuthData auth = new AuthData(user.username(), generateAuthToken());
        authDataAccess.createAuth(auth);

        RegisterResult res = new RegisterResult(auth.username(), auth.authToken());
        return res;
    }

    public LoginResult login(LoginRequest req) throws Exception{
        if(!userDataAccess.getUser(req.username()).password().equals(req.password())){
            throw new Exception("wrong password");
        }

        AuthData auth = new AuthData(req.username(), generateAuthToken());
        authDataAccess.createAuth(auth);

        LoginResult res = new LoginResult(auth.username(), auth.authToken());
        return res;
    }

    public LogoutResult logout(LogoutRequest req) throws Exception{
        if(authDataAccess.getAuth(req.authToken()) != null){
            throw new Exception("username already taken");
        }

        authDataAccess.deleteAuth(req.authToken());

        LogoutResult res = new LogoutResult();
        return res;
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}
