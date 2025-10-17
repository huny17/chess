package services;

import dataaccess.GameDAO;
import model.AuthData;
import model.request.CreateGameRequest;
import model.request.JoinGameRequest;
import model.request.ListGamesRequest;
import model.result.CreateGameResult;
import model.result.JoinGameResult;
import model.result.ListGamesResult;
import model.result.RegisterResult;

public class GameService {

    private GameDAO gameDataAccess;

    public class GameService {
        public CreateGameResult create(CreateGameRequest createGameRequest){
            return result;
        }
        public JoinGameResult join(JoinGameRequest joinGameRequest){}
        public ListGamesResult list(ListGamesRequest listGamesRequest){}
    }

}
