package dataaccess;

import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {
    void clear() throws DataAccessException;
    void createAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(String authToken);
    public String getUser(String authToken);
    void deleteAuth(String authToken);
    public HashMap<String, AuthData> getAuthentications() throws DataAccessException;
}
