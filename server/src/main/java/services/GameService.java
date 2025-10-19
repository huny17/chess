package services;

import chess.ChessGame;
import dataaccess.GameDAO;
import dataaccess.GeneralException;
import model.GameData;
import model.request.CreateGameRequest;
import model.request.JoinGameRequest;
import model.request.ListGamesRequest;
import model.result.CreateGameResult;
import model.result.JoinGameResult;
import model.result.ListGamesResult;
import model.result.RegisterResult;

public class GameService {

    private GameDAO gameDataAccess;

    public GameService(GameDAO gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public CreateGameResult create(CreateGameRequest req) throws GeneralException{
        int index = 0;

        if(gameDataAccess.getGame(req.gameName()) != null){
            throw new GeneralException("403","Username already taken");
        }

        GameData game = new GameData(index,null,null,req.gameName(), new ChessGame());
        gameDataAccess.createGame(game);

        CreateGameResult res = new CreateGameResult("" + game.id());

        index += 1;

        return res;
    }

    public JoinGameResult join(JoinGameRequest req) throws GeneralException {
        if(req.playerColor() == "WHITE" && gameDataAccess.getWhiteUser(req.gameId()) != null){
            throw new GeneralException("403","Color already taken");
        }
        if(req.playerColor() == "BLACK" && gameDataAccess.getBlackUser(req.gameId()) != null){
            throw new GeneralException("403","Color already taken");
        }
        JoinGameResult res = updateColor(req);
        return res;
    }

    public ListGamesResult list(ListGamesRequest req){


        ListGamesResult res = new ListGamesResult();

        return res;
    }

    public JoinGameResult updateColor(JoinGameRequest req){
        if(req.playerColor() == "WHITE"){
            GameData oldGame = gameDataAccess.getGame(req.gameId());
            GameData newGame = new GameData(oldGame.id(), req.playerColor(), oldGame.blackUser(), oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateGame(req.gameId(), newGame);
        }
        if(req.playerColor() == "BLACK"){
            GameData oldGame = gameDataAccess.getGame(req.gameId());
            GameData newGame = new GameData(oldGame.id(), oldGame.whiteUser(), req.playerColor(), oldGame.gameName(), oldGame.chessGame());
            gameDataAccess.updateGame(req.gameId(), newGame);
        }
        return new JoinGameResult();
    }
}
