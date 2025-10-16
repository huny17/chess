package dataaccess;

import model.UserData;

public interface UserDAO {
    void clear();
    void createUser(UserData user);
    UserData getUser(String username);

}
