package passoff;

import dataaccess.GeneralException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
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
            assertNotNull(res);
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
            assertNotNull(res);
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
    public void createGameCorrect(){
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
            var expected = new CreateGameResult(1);
            assertNotNull(res);
            assertEquals(expected, res);
        });
    }

    @Test
    public void createGameEmpty(){
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

    @Test
    public void joinGameCorrect(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            LoginResult login = userService.login(new LoginRequest("user", "word"));
            CreateGameResult id = gameService.createGame(new CreateGameRequest("name"));
            var res = gameService.joinGame(new JoinGameRequest("WHITE", Integer.toString(id.gameID())), login.authToken());
            Assertions.assertNotNull(res);
        });
    }

    @Test
    public void joinGameTeamColor(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertThrows(GeneralException.class, ()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            LoginResult login = userService.login(new LoginRequest("user", "word"));
            CreateGameResult id = gameService.createGame(new CreateGameRequest("name"));
            gameService.joinGame(new JoinGameRequest("WHITE", Integer.toString(id.gameID())), login.authToken());
            gameService.joinGame(new JoinGameRequest("WHITE", Integer.toString(id.gameID())), login.authToken());
        });
    }

    @Test
    public void joinGameNullID(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertThrows(GeneralException.class, ()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            LoginResult login = userService.login(new LoginRequest("user", "word"));
            gameService.createGame(new CreateGameRequest("name"));
            gameService.joinGame(new JoinGameRequest("WHITE", null), login.authToken());
        });
    }

    @Test
    public void joinGameNonExistent(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertThrows(GeneralException.class, ()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            LoginResult login = userService.login(new LoginRequest("user", "word"));
            gameService.joinGame(new JoinGameRequest("WHITE", "1"), login.authToken());
        });
    }

    @Test
    public void listGameCorrect(){
        var userDataAccess = new MemoryUserDAO();
        var authDataAccess = new MemoryAuthDAO();
        var gameDataAccess = new MemoryGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);

        assertDoesNotThrow(()-> {
            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            LoginResult login = userService.login(new LoginRequest("user", "word"));
            CreateGameResult id = gameService.createGame(new CreateGameRequest("name"));
            gameService.createGame(new CreateGameRequest("LOL"));
            gameService.createGame(new CreateGameRequest("I shall win"));
            gameService.joinGame(new JoinGameRequest("WHITE", Integer.toString(id.gameID())), login.authToken());
            ListGamesResult res = gameService.listGames();
            Assertions.assertNotNull(res);
        });
    }

}
