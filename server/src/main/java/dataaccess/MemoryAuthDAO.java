package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    private HashMap<String, UserData> authentications = new HashMap<>();

    @Override
    public void clear() {
        authentications.clear();
    }
}
