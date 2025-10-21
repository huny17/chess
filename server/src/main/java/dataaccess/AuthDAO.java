package dataaccess;

import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {
    void clear();
    void createAuth(AuthData auth);
    AuthData getAuth(String authToken);
    void deleteAuth(String authToken);
    public HashMap<String, AuthData> getAuthentications();
}
