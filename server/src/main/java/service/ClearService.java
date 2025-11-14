package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import exceptions.*;

public class ClearService {

    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameAuthData;

    public ClearService(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameAuthData){
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameAuthData = gameAuthData;
    }

    public void clear() throws GeneralException {
        userDataAccess.clear();
        authDataAccess.clear();
        gameAuthData.clear();
    }

}
