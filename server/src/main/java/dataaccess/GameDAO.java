package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {
    void clear();
    void createGame(GameData game);
    GameData getGame(String gameId);
    List listGames();

}
