package dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public interface DataAccess {
    void clear();
    void createUser(UserData user);
    UserData getUser(String username);

}
