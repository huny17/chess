package dataaccess;

import model.AuthData;
import java.util.HashMap;
import Exceptions.*;

public interface AuthDAO {
    void clear() throws GeneralException;
    void createAuth(AuthData auth) throws GeneralException;
    AuthData getAuth(String authToken) throws GeneralException;
    public String getUser(String authToken) throws GeneralException;
    void deleteAuth(String authToken) throws GeneralException;
    public HashMap<String, AuthData> getAuthentications() throws GeneralException;
}
