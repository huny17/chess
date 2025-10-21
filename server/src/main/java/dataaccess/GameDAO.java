package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear();
    String createGame(String gameName);
    GameData getGame(String gameId);
    String getBlackUser(String gameId);
    String getWhiteUser(String gameId);
    Collection<GameData> listGames();
    void updateGame(String gameId, GameData newGame);
}
