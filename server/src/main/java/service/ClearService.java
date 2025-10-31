package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {

    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameAuthData;

    public ClearService(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameAuthData){
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameAuthData = gameAuthData;
    }

    public void clear() throws DataAccessException {
        userDataAccess.clear();
        authDataAccess.clear();
        gameAuthData.clear();
    }

}
