package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public interface GameDAO {
    void clear();
    void createGame(GameData game);
    GameData getGame(String gameName);
    Collection<GameData> listGames();
    void updateGame();
}
