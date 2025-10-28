package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MySQLGameDAO implements GameDAO {

    private final ExecuteUpdate update = new ExecuteUpdate();
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private Integer index = 1;

    @Override
    public void clear() throws DataAccessException{
        String statement = "TRUNCATE game";
        update.executeUpdate(statement);
    }

    @Override
    public Integer createGame(String gameName) throws DataAccessException{
        var statement = "INSERT INTO user (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)";
        int id = update.executeUpdate(statement, NULL, NULL, gameName, new ChessGame());
        return id;
    }

    @Override
    public String getBlackUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.blackUsername();
    }

    @Override
    public String getWhiteUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.whiteUsername();
    }

    @Override
    public GameData getGame(String gameId) {
        return games.get(Integer.parseInt(gameId));
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void updateGame(String gameId, GameData newGame) {
        games.put(Integer.parseInt(gameId), newGame);
    }

}
