package dataaccess;

import model.UserData;
import com.google.gson.Gson;
import model.UserData;

import java.sql.*;
import java.util.HashMap;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLUserDAO implements UserDAO {

    private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clear(){
        users.clear();
    }

    @Override
    public UserData createUser(UserData user) {
        var statement = "INSERT INTO user (name, password, email) VALUES (?, ?, ?)";
        String json = new Gson().toJson(user);
        return new UserData(user.username(), user.password(), user.email());
    }

    @Override
    public UserData getUser(String username) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM pet WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username); //getting value
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readPet(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(ResponseException.Code.ServerError, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException{
        var username = rs.getString("username");
        var
        UserData user = new Gson().fromJson(json, UserData.class);
    }

    @Override
    public HashMap<String, UserData> getUserMap(){
        return users;
    }

}