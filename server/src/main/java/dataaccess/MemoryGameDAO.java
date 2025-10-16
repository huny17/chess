package dataaccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO implements GameDAO{

    private HashMap<String, GameData> games = new HashMap<>();

    @Override
    public void clear(){
        games.clear();
    }

    @Override
    public void createGame(GameData game) {
        games.put();
    }

    @Override
    public GameData getGame(GameData game) {
        return games.get(game);
    }

    @Override
    public List listGames() {
        return List.of(games);
    }

    @Override
    public void updateGame() {

    }
}
