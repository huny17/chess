package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.HashMap;

public class MySQLUserDAO implements UserDAO {

    public MySQLUserDAO() throws DataAccessException{
        configureUserTable();
    }

    private final ExecuteUpdate update = new ExecuteUpdate();
    private final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clear() throws DataAccessException{
        String statement = "TRUNCATE user";
        users.clear();
        update.executeUpdate(statement);
    }

    @Override
    public void createUser(UserData user) throws DataAccessException{
        configureUserTable();
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)"; //How is json being used?
        String hashPassword = hashUserPassword(user.password());
        update.executeUpdate(statement, user.username(), hashPassword, user.email());
    }

    String hashUserPassword(String clearTextPassword) {
        return BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
    }

    public boolean verifyUser(String username, String providedClearTextPassword) throws DataAccessException{
        String hashedPassword = readHashedPasswordFromDatabase(username);

        return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
    }

    private String readHashedPasswordFromDatabase(String username) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT password FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("password");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException{
        var username = rs.getString("username");
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new UserData(username, password, email);
    }

    private final String[] createUserTable = {"""
        CREATE TABLE IF NOT EXISTS user (
            username VARCHAR(128) NOT NULL,
            password VARCHAR(128) NOT NULL,
            email VARCHAR(128) NOT NULL,
            PRIMARY KEY (username)
        )"""
    };

    public void configureUserTable() throws DataAccessException {
        update.configureDatabase(createUserTable);
    }


    @Override
    public HashMap<String, UserData> getUserMap() throws DataAccessException{

        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        //for(var user : rs.next()){
                        users.put(readUser(rs).username(),readUser(rs));
                    }
                }
                return users;
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
    }

}