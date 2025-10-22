package passoff.server;

import dataaccess.GeneralException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.UserData;
import model.request.*;
import model.result.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.GameService;
import services.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    @Test
    public void registerCorrect(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            var res = userService.register(new RegisterRequest("user", "word", "u@u"));
            Assertions.assertNotNull(res);
        });
    }

    @Test
    public void registerWithoutEmail() throws GeneralException{
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        assertThrows(GeneralException.class, ()-> userService.register(new RegisterRequest("user","word", null)));
    }

    @Test
    public void logoutCorrect(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
        });
    }

    @Test
    public void logoutWrong() throws GeneralException{
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
        userService.logout("");
    }

    @Test
    public void loginCorrect(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            var res = userService.login(new LoginRequest("user", "word"));
            Assertions.assertNotNull(res);
        });
    }

    @Test
    public void loginWithoutPassword() throws GeneralException{
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);

        model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
        userService.logout(register.authToken());
        assertThrows(GeneralException.class, ()-> userService.login(new LoginRequest("user",null)));
    }

    @Test
    public void clearData(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);

        assertDoesNotThrow(()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            clearService.clear();
        });
    }

    @Test
    public void CreateGameCorrect(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            userService.login(new LoginRequest("user", "word"));
            var res = gameService.createGame(new CreateGameRequest("name"));
            Assertions.assertNotNull(res);
        });
    }

    @Test
    public void CreateGameEmpty(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
                    model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
                    userService.logout(register.authToken());
                    userService.login(new LoginRequest("user", "word"));
                });
        assertThrows(GeneralException.class, ()-> gameService.createGame(new CreateGameRequest(null)));
    }

}
