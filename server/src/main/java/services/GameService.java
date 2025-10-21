package services;

import dataaccess.GameDAO;
import dataaccess.GeneralException;
import model.GameData;
import model.request.*;
import model.result.*;

public class GameService {

    private final GameDAO gameDataAccess;

    public GameService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public CreateGameResult createGame(CreateGameRequest req) throws GeneralException{

        if(gameDataAccess.getGame(req.gameName()) != null){
            throw new GeneralException("403","Username already taken");
        }

        String gameId = gameDataAccess.createGame(req.gameName());

        return new CreateGameResult(gameId);
    }

    public JoinGameResult joinGame(JoinGameRequest req) throws GeneralException {
        if(req.playerColor().equals("WHITE") && gameDataAccess.getWhiteUser(req.gameId()) != null){
            throw new GeneralException("403","Color already taken");
        }
        if(req.playerColor().equals("BLACK") && gameDataAccess.getBlackUser(req.gameId()) != null){
            throw new GeneralException("403","Color already taken");
        }
        return updateColor(req);
    }

    public ListGamesResult listGames(ListGamesRequest req){

        return new ListGamesResult("games", gameDataAccess.listGames());
    }

    public JoinGameResult updateColor(JoinGameRequest req){
        if(req.playerColor().equals("WHITE")){
            GameData oldGame = gameDataAccess.getGame(req.gameId());
            GameData newGame = new GameData(oldGame.id(), req.playerColor(), oldGame.blackUser(), oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateGame(req.gameId(), newGame);
        }
        if(req.playerColor().equals("BLACK")){
            GameData oldGame = gameDataAccess.getGame(req.gameId());
            GameData newGame = new GameData(oldGame.id(), oldGame.whiteUser(), req.playerColor(), oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateGame(req.gameId(), newGame);
        }
        return new JoinGameResult();
    }

}
