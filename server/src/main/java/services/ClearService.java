package services;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;

public class ClearService {

    private UserDAO userDataAccess;
    private AuthDAO authDataAccess;
    private final GameDAO gameAuthData;

    public ClearService(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameAuthData){
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameAuthData = gameAuthData;
    }

    public void clear(){
        userDataAccess.clear();
        authDataAccess.clear();
        gameAuthData.clear();
    }

}
