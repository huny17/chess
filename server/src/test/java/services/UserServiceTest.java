package services;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import model.request.RegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void clear() {
    }

    @Test
    void register() {
        var user = new RegisterRequest("joe","j@j","j");
        var at = "xyz";
        var userDA = new MemoryUserDAO();
        var authDA = new MemoryAuthDAO();
        var service = new UserService(userDA, authDA);
        assertDoesNotThrow(()-> {
            AuthData res = service.register(user);
            assertNotNull(res);
            assertEquals(res.username(), user.username());
            assertNotNull(res.authToken());
            assertEquals(String.class, res.authToken().getClass());
        });
    }
}