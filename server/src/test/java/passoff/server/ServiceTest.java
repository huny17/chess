package passoff.server;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import model.request.RegisterRequest;
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
            var res = userService.register(new RegisterRequest("cow", "rat", "j@j"));
            Assertions.assertNotNull(res);
        });
    }

    @Test
    public void registerWithoutPassword(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        assertThrows( userService.register(new RegisterRequest("cow",null, "rat")));
    }
}
