package dataaccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear();
    UserData createUser(UserData user);
    UserData getUser(String username);
    public HashMap<String, UserData> getUserMap();
}
