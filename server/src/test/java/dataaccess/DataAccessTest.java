package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTest {

    @Test
    void clear() {
        var user = new UserData("joe", "j@j", "j");
        DataAccess da = new MemoryDataAccess();
        da.createUser(user);
        assertNotNull(da.getUser(user.username()));
        da.clear();
        assertNull(da.getUser(user.username()));
    }

    @Test
    void createUser() {
    }

    @Test
    void getUser() {
    }
}