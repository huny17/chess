package dataaccess;

import model.GameData;
import Exceptions.*;
import java.util.Collection;

public interface GameDAO {
    void clear() throws GeneralException;
    Integer createGame(String gameName) throws GeneralException;
    GameData getGame(int gameID) throws GeneralException;
    String getBlackUser(String gameID) throws GeneralException;
    String getWhiteUser(String gameID) throws GeneralException;
    Collection<GameData> listGames() throws GeneralException;
    void updateGame(String gameID, GameData newGame) throws GeneralException;
    void updateWhiteTeam(String gameID, GameData game) throws GeneralException;
    void updateBlackTeam(String gameID, GameData game) throws GeneralException;
}
