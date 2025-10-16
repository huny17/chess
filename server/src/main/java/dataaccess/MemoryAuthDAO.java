package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    private HashMap<String, AuthData> authentications = new HashMap<>();

    @Override
    public void clear() {
        authentications.clear();
    }

    @Override
    public void createAuth(AuthData auth) {
        authentications.put(auth.username(), auth);
    }

    @Override
    public AuthData getAuth(String username) {
        return authentications.get(username);
    }
}
