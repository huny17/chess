package dataaccess;

import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLAuthDAO implements AuthDAO{

    private final ExecuteUpdate update = new ExecuteUpdate();
    private final HashMap<String, AuthData> authentications = new HashMap<>();

    @Override
    public void clear() throws DataAccessException{
        String statement = "TRUNCATE auth";
        update.executeUpdate(statement);
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException{
        var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)"; //How is json being used?
        update.executeUpdate(statement, auth.username(), auth.authToken());
    }

    //want return user or token?
    @Override
    public AuthData getAuth(String authToken) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM auth WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public String getUser(String authToken) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM auth WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs).username();
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException{
        var statement = "DELETE FROM auth WHERE authToken=?";
        update.executeUpdate(statement, authToken);
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var authToken = rs.getString("authToken");
        return new AuthData(username, authToken);
    }

    private final String[] createAuthTable = {"""
                CREATE TABLE IF NOT EXISTS auth (
                    authToken VARCHAR(128) NOT NULL,
                    username VARCHAR(128) NOT NULL,
                    PRIMARY KEY (authToken)
                )"""
    };

    private void configureDatabase() throws DataAccessException {
        update.configureDatabase(createAuthTable);
    }

    @Override
    public HashMap<String, AuthData> getAuthentications() throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        //for(var user : rs.next()){
                        authentications.put(readAuth(rs).username(),readAuth(rs));
                    }
                }
                return authentications;
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
    }
}

