package dataaccess;

import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void createAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    public String getUser(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    public HashMap<String, AuthData> getAuthentications() throws DataAccessException;
}
