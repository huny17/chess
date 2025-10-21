package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    private final HashMap<String, AuthData> authentications = new HashMap<>();

    @Override
    public void clear() {
        authentications.clear();
    }

    @Override
    public void createAuth(AuthData auth) {
        authentications.put(auth.authToken(), auth);
    }

    //want return user or token?
    @Override
    public AuthData getAuth(String authToken) {
        return authentications.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        authentications.remove(authToken);
    }

    @Override
    public HashMap<String, AuthData> getAuthentications(){
        return authentications;
    }
}
