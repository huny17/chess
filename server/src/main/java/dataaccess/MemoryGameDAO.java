package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    private final HashMap<Integer, GameData> games = new HashMap<>();
    private int index = 1;

    @Override
    public void clear(){
        games.clear();
    }

    @Override
    public int createGame(String gameName) {
        //increment in here
        //create game here
        GameData game = new GameData(index,null,null, gameName, new ChessGame());
        index += 1;
        games.put(game.ID(), game);
        return game.ID();
    }

    @Override
    public String getBlackUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.blackUsername();
    }

    @Override
    public String getWhiteUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.whiteUsername();
    }

    @Override
    public GameData getGame(String gameId) {
        return games.get(Integer.parseInt(gameId));
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void updateGame(String gameId, GameData newGame) {
        games.put(Integer.parseInt(gameId), newGame);
    }

}
