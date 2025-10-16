package services;

import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void clear() {
    }

    @Test
    void register() {
        var user = new UserData("joe","j@j","j");
        var at = "xyz";
        var da = new MemoryUserDAO();
        var service = new UserService(da);
        assertDoesNotThrow(()-> {
            AuthData res = service.register(user);
            assertNotNull(res);
            assertEquals(res.username(), user.username());
            assertNotNull(res.authToken());
            assertEquals(String.class, res.authToken().getClass());
        });
    }
}