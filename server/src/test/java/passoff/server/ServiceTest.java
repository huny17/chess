package passoff.server;

import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.UserService;

public class ServiceTest {

    @Test
    public void registerNormal(){
        var dataAccess = new MemoryUserDAO();
        var userService = new UserService(dataAccess);

        var res = userService.register(new UserData("cow", "rat", "john"));
        Assertions.assertNotNull(res);
    }

}
