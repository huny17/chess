package dataaccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear() throws DataAccessException;
    UserData createUser(UserData user);
    UserData getUser(String username) throws DataAccessException;
    public HashMap<String, UserData> getUserMap() throws DataAccessException;
}
