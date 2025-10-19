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
        games.put(game.id(), game);
    }

    @Override
    public GameData getGame(String gameName) {
        return games.get(gameName);
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void updateGame() {

    }
}
