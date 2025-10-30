package memoryDAO;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

    private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clear(){
        users.clear();
    }

    @Override
    public void createUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public HashMap<String, UserData> getUserMap(){
        return users;
    }

    @Override
    public boolean verifyUser(String username, String providedClearTextPassword){
        return true;
    }

}
