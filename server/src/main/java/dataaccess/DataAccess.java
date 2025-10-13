package dataaccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public interface DataAccess {

    void saveUser(UserData user);

}
