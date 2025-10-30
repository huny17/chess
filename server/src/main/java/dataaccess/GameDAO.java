package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    Integer createGame(String gameName) throws DataAccessException;
    GameData getGame(String gameID) throws DataAccessException;
    String getBlackUser(String gameID) throws DataAccessException;
    String getWhiteUser(String gameID) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateGame(String gameID, GameData newGame) throws DataAccessException;
}
