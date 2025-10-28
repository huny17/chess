package dataaccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear() throws DataAccessException;
    void createUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    public HashMap<String, UserData> getUserMap() throws DataAccessException;
    public boolean verifyUser(String username, String providedClearTextPassword) throws DataAccessException;
}
