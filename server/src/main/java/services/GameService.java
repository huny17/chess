package services;

import dataaccess.GameDAO;

public class GameService {

    private GameDAO gameDataAccess;

    public GameService(GameDAO gameDataAccess){
        this.gameDataAccess = gameDataAccess;
    }



}
