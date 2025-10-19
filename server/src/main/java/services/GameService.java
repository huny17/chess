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
        if (checkColorUser(req)) {

            GameData game = gameDataAccess.getGame(req.gameId());



            JoinGameResult res = new JoinGameResult();

        return res;
        }
    }

    public ListGamesResult list(ListGamesRequest req){
        if(gameDataAccess.createGame(req.gameName()) != null){
            throw new GeneralException("403","Username already taken");
        }



        ListGamesResult res = new ListGamesResult();

        return res;
    }


    public Boolean checkColorUser(JoinGameRequest req) throws GeneralException{
        if(req.playerColor() == "WHITE" && gameDataAccess.getWhiteUser(req.gameId()) != null){
            throw new GeneralException("403","Color already taken");
        }
        if(req.playerColor() == "BLACK" && gameDataAccess.getBlackUser(req.gameId()) != null){
            throw new GeneralException("403","Color already taken");
        }
        return true;
    }
}
