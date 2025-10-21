package dataaccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear();
    void createUser(UserData user);
    UserData getUser(String username);
    public HashMap<String, UserData> getUserMap();
}
