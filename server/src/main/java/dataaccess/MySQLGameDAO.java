package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
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

    public MySQLGameDAO() throws DataAccessException{
        configureGameTable();
    }

    @Override
    public void clear() throws DataAccessException{
        String statement = "TRUNCATE game";
        games.clear();
        update.executeUpdate(statement);
    }

    @Override
    public Integer createGame(String gameName) throws DataAccessException{
        configureGameTable();
        var statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)";
        return update.executeUpdate(statement, null, null, gameName, serializeChessGame(new ChessGame()));
    }

    @Override
    public String getBlackUser(String gameID) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE gameID=?";
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
            var statement = "SELECT * FROM game WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, Integer.parseInt(gameID)); //getting value
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
    public GameData getGame(int gameID) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {

                        GameData g = readGame(rs);
                        return g;
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
            var statement = "SELECT * FROM game";
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
    public void updateGame(String gameID, GameData newGame) throws DataAccessException{
        ChessGame theGame = newGame.chessGame();

        var statement = "UPDATE game SET chessGame=? WHERE gameID=?";
        update.executeUpdate(statement, serializeChessGame(theGame), Integer.parseInt(gameID));
    }

    @Override
    public void updateWhiteTeam(String gameID, GameData game) throws DataAccessException{
            var statement = "UPDATE game SET whiteUsername=? WHERE gameID=?";
            update.executeUpdate(statement, game.whiteUsername(), Integer.parseInt(gameID));

    }

    @Override
    public void updateBlackTeam(String gameID, GameData game) throws DataAccessException{
        var statement = "UPDATE game SET blackUsername=? WHERE gameID=?";
        update.executeUpdate(statement, game.blackUsername(), Integer.parseInt(gameID));
    }


    private GameData readGame(ResultSet rs) throws SQLException {
        Integer gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var chessGame = rs.getString("chessGame");// need to serialize and deserialize
        return new GameData(gameID, whiteUsername, blackUsername, gameName, deserializeChessGame(chessGame));
    }

    private ChessGame deserializeChessGame(String game){
        return new Gson().fromJson(game, ChessGame.class);
    }

    private String serializeChessGame(ChessGame game){
        return new Gson().toJson(game);
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

    public void configureGameTable() throws DataAccessException {
        update.configureDatabase(createGameTable);
    }

}
