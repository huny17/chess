package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
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

    @Override
    public void clear() throws DataAccessException{
        String statement = "TRUNCATE game";
        update.executeUpdate(statement);
    }

    @Override
    public Integer createGame(String gameName) throws DataAccessException{
        var statement = "INSERT INTO user (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)";
        String chessGame = new Gson().toJson(new ChessGame());
        int gameId = update.executeUpdate(statement, NULL, NULL, gameName, chessGame);
        return gameId;
    }

    @Override
    public String getBlackUser(String gameID) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameID); //getting value
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
    public String getWhiteUser(String gameID) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameID); //getting value
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
    public GameData getGame(String gameId) throws DataAccessException{
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
    public Collection<GameData> listGames() throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        //for(var user : rs.next()){
                        games.put(readGame(rs).gameID(), readGame(rs));
                    }
                }
                return games.values();
            }
    } catch (Exception e) {
        throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
    }
    }

    @Override
    public void updateGame(String gameId, GameData newGame) {

        games.put(Integer.parseInt(gameId), newGame);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        Integer gameId = rs.getInt("gameId");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var chessGame = rs.getLong("chessGame");// need to serialize and deserialize
        return new GameData(gameId, whiteUsername, blackUsername, gameName, chessGame);
    }

    private final String[] createGameTable = {"""
        CREATE TABLE IF NOT EXISTS game (
            gameID INT NOT NULL AUTO_INCREMENT,
            whiteUsername VARCHAR(128) NULL,
            blackUsername VARCHAR(128) NULL,
            gameName VARCHAR(45) NULL,
            chessGame LONGTEXT NOT NULL,
            state VARCHAR(45) NULL,
            description VARCHAR(256) NULL,
            PRIMARY KEY (gameID)
        )"""
    };

}
