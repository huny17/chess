package passoff.server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    @Test
    public void registerNormal(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            var res = userService.register(new UserData("cow", "rat", "j@j"));
            Assertions.assertNotNull(res);
        });
    }

}
