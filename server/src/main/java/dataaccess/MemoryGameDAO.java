package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    private HashMap<String, GameData> games = new HashMap<>();

    @Override
    public void clear(){
        games.clear();
    }

    @Override
    public void createGame(GameData game) {
        games.put(game.name(), game);
    }

    @Override
    public GameData getGame(GameData game) {
        return games.get(game.name());
    }

    @Override
    public HashMap<String, GameData> listGames() {
        return games;
    }

    @Override
    public void updateGame() {

    }
}
