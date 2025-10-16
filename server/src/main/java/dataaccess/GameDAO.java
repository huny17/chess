package dataaccess;

import model.GameData;

import java.util.HashMap;

public interface GameDAO {
    void clear();
    void createGame(GameData game);
    GameData getGame(GameData game);
    HashMap<String, GameData> listGames();
    void updateGame();
}
