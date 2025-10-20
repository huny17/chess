package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void clear(){
        games.clear();
    }

    @Override
    public void createGame(GameData game) {
        //increment in here

        //create game here

        games.put(game.id(), game);
    }

    @Override
    public String getBlackUser(String gameId) {
        GameData game = games.get(gameId);
        return game.blackUser();
    }

    @Override
    public String getWhiteUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.whiteUser();
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
