package service;


import Exceptions.GeneralException;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
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

    public CreateGameResult createGame(CreateGameRequest req) throws GeneralException {
        if(req.gameName() == null){
            throw new GeneralException(GeneralException.ExceptionType.invalid,"Please name your game");
        }

        int gameID = gameDataAccess.createGame(req.gameName());

        return new CreateGameResult(gameID);
    }

    public JoinGameResult joinGame(JoinGameRequest req, String authToken) throws GeneralException {
        if(req.gameID() == null | req.playerColor() ==  null){
            throw new GeneralException(GeneralException.ExceptionType.invalid,"Please choose a Game ID or Team Color");
        }
        if(gameDataAccess.getGame(Integer.parseInt(req.gameID())) == null){
            throw new GeneralException(GeneralException.ExceptionType.invalid,"Game ID does not exist");
        }
        if(!req.playerColor().equals("WHITE") && !req.playerColor().equals("BLACK")){
            throw new GeneralException(GeneralException.ExceptionType.invalid,"Team Color is invalid");
        }
        if(req.playerColor().equals("WHITE") && gameDataAccess.getWhiteUser(req.gameID()) != null){
            throw new GeneralException(GeneralException.ExceptionType.forbidden,"Color already taken");
        }
        if(req.playerColor().equals("BLACK") && gameDataAccess.getBlackUser(req.gameID()) != null){
            throw new GeneralException(GeneralException.ExceptionType.forbidden,"Color already taken");
        }

        String user = authDataAccess.getUser(authToken);

        return updateColor(req, user);
    }

    public ListGamesResult listGames() throws GeneralException{

        return new ListGamesResult(gameDataAccess.listGames());
    }

    public JoinGameResult updateColor(JoinGameRequest req, String user) throws GeneralException{
        if(req.playerColor().equals("WHITE")){
            GameData oldGame = gameDataAccess.getGame(Integer.parseInt(req.gameID()));
            GameData newGame = new GameData(oldGame.gameID(), user, oldGame.blackUsername(), oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateWhiteTeam(req.gameID(), newGame);
        }
        if(req.playerColor().equals("BLACK")){
            GameData oldGame = gameDataAccess.getGame(Integer.parseInt(req.gameID()));
            GameData newGame = new GameData(oldGame.gameID(), oldGame.whiteUsername(), user, oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateBlackTeam(req.gameID(), newGame);
        }
        return new JoinGameResult("{}");
    }

}
