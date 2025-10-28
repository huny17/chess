package dataaccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import static java.sql.Types.NULL;

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
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameId); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs).blackUsername();
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public String getWhiteUser(String gameId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameId); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs).whiteUsername();
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public GameData getGame(String gameId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameId); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void updateGame(String gameId, GameData newGame) {
        games.put(Integer.parseInt(gameId), newGame);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var chessGame = rs.getLong("chessGame");// need to serialize and deserialize
        return new GameData(id, whiteUsername, blackUsername, gameName, chessGame);
    }

}
