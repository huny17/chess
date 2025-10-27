package dataaccess;

import model.UserData;
import com.google.gson.Gson;
import model.UserData;
import ExecuteUpdate;
import java.sql.*;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class MySQLUserDAO implements UserDAO {

    private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clear() throws DataAccessException{
        String statement = "TRUNCATE user";
        executeUpdate(statement);
    }

    @Override
    public UserData createUser(UserData user) {
        var statement = "INSERT INTO user (name, password, email, json) VALUES (?, ?, ?)"; //How is json being used?
        String json = new Gson().toJson(user);
        return new UserData(user.username(), user.password(), user.email());
    }

    @Override
    public UserData getUser(String username) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM pet WHERE username=?";
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

//    public void deleteUser(String username) throws DataAccessException{
//        var statement = "DELETE FROM user WHERE username=?";
//        executeUpdate(statement, username);
//    }


    private final String[] createUserTable = {"""
                CREATE TABLE IF NOT EXISTS user (
                    username VARCHAR(128) NOT NULL,
                    password VARCHAR(128) NOT NULL,
                    email VARCHAR(128) NOT NULL,
                    PRIMARY KEY (username)
                )"""
    };


    @Override
    public HashMap<String, UserData> getUserMap(){
        return users;
    }

}