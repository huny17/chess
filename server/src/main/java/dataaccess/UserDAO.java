package dataaccess;

import model.UserData;
import exceptions.*;
import java.util.HashMap;

public interface UserDAO {
    void clear() throws GeneralException;
    void createUser(UserData user) throws GeneralException;
    UserData getUser(String username) throws GeneralException;
    public HashMap<String, UserData> getUserMap() throws GeneralException;
    public boolean verifyUser(String username, String providedClearTextPassword) throws GeneralException;
}
