package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    Integer createGame(String gameName) throws DataAccessException;
    GameData getGame(String gameId) throws DataAccessException;
    String getBlackUser(String gameId) throws DataAccessException;
    String getWhiteUser(String gameId) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateGame(String gameId, GameData newGame) throws DataAccessException;
}
