package services;

import dataaccess.*;
import dataaccess.GeneralException;
import model.*;
import model.request.*;
import model.result.*;

public class GameService {

    private final GameDAO gameDataAccess;
    private final AuthDAO authDataAccess;

    public GameService(GameDAO gameDataAccess, AuthDAO authDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
    }

    public CreateGameResult createGame(CreateGameRequest req) throws GeneralException{
        if(req.gameName() == null){
            throw new GeneralException("400","Please name your game");
        }

        int gameId = gameDataAccess.createGame(req.gameName());

        return new CreateGameResult(gameId);
    }

    public JoinGameResult joinGame(JoinGameRequest req, String authToken) throws GeneralException {
        if(req.gameID() == null | req.playerColor() ==  null){
            throw new GeneralException("400","Please choose a Game ID or Team Color");
        }
        if(gameDataAccess.getGame(req.gameID()) == null){
            throw new GeneralException("400","Game ID does not exist");
        }
        if(!req.playerColor().equals("WHITE") && !req.playerColor().equals("BLACK")){
            throw new GeneralException("400","Team Color is invalid");
        }
        if(req.playerColor().equals("WHITE") && gameDataAccess.getWhiteUser(req.gameID()) != null){
            throw new GeneralException("403","Color already taken");
        }
        if(req.playerColor().equals("BLACK") && gameDataAccess.getBlackUser(req.gameID()) != null){
            throw new GeneralException("403","Color already taken");
        }

        String user = authDataAccess.getUser(authToken);

        return updateColor(req, user);
    }

    public ListGamesResult listGames(){

        return new ListGamesResult(gameDataAccess.listGames());
    }

    public JoinGameResult updateColor(JoinGameRequest req, String user) throws GeneralException{
        if(req.playerColor().equals("WHITE")){
            GameData oldGame = gameDataAccess.getGame(req.gameID());
            GameData newGame = new GameData(oldGame.gameID(), user, oldGame.blackUsername(), oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateGame(req.gameID(), newGame);
        }
        if(req.playerColor().equals("BLACK")){
            GameData oldGame = gameDataAccess.getGame(req.gameID());
            GameData newGame = new GameData(oldGame.gameID(), oldGame.whiteUsername(), user, oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateGame(req.gameID(), newGame);
        }
        return new JoinGameResult("{}");
    }

}
