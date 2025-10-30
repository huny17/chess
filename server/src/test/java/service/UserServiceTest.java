package service;

import dataaccess.GeneralException;
import dataaccess.*;
import memoryDAO.MemoryAuthDAO;
import memoryDAO.MemoryGameDAO;
import memoryDAO.MemoryUserDAO;
import model.request.*;
import model.result.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void registerCorrect(){
        assertDoesNotThrow(()-> {
        var userDataAccess = new MySQLUserDAO();
        var authDataAccess = new MySQLAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);


            var res = userService.register(new RegisterRequest("user", "word", "u@u"));
            assertNotNull(res);
        });
    }

    @Test
    public void registerWithoutEmail() throws GeneralException{
        UserDAO userDataAccess; //mySQL
        AuthDAO authDataAccess;

        try {
            userDataAccess = new MySQLUserDAO(); //mySQL
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            authDataAccess = new MemoryAuthDAO();
        }

        var userService = new UserService(userDataAccess, authDataAccess);

        assertThrows(GeneralException.class, ()-> userService.register(new RegisterRequest("user","word", null)));
    }

    @Test
    public void logoutCorrect(){
        assertDoesNotThrow(()-> {
        var userDataAccess = new MySQLUserDAO();
        var authDataAccess = new MySQLAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);


            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
        });
    }

    @Test
    public void logoutWrong() throws GeneralException, DataAccessException{
        UserDAO userDataAccess; //mySQL
        AuthDAO authDataAccess;

        try {
            userDataAccess = new MySQLUserDAO(); //mySQL
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            authDataAccess = new MemoryAuthDAO();
        }
        var userService = new UserService(userDataAccess, authDataAccess);

        model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
        userService.logout("");
    }

    @Test
    public void loginCorrect(){
        assertDoesNotThrow(()-> {
        var userDataAccess = new MySQLUserDAO();
        var authDataAccess = new MySQLAuthDAO();
        var userService = new UserService(userDataAccess, authDataAccess);


            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            userService.logout(register.authToken());
            var res = userService.login(new LoginRequest("user", "word"));
            assertNotNull(res);
        });
    }

    @Test
    public void loginWithoutPassword() throws GeneralException, DataAccessException{
        UserDAO userDataAccess; //mySQL
        AuthDAO authDataAccess;

        try {
            userDataAccess = new MySQLUserDAO(); //mySQL
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            authDataAccess = new MemoryAuthDAO();
        }
        var userService = new UserService(userDataAccess, authDataAccess);

        model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
        userService.logout(register.authToken());
        assertThrows(GeneralException.class, ()-> userService.login(new LoginRequest("user",null)));
    }

    @Test
    public void clearData(){
        assertDoesNotThrow(()-> {
        var userDataAccess = new MySQLUserDAO();
        var authDataAccess = new MySQLAuthDAO();
        var gameDataAccess = new MySQLGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);


            model.result.RegisterResult register = userService.register(new RegisterRequest("user", "word", "u@u"));
            clearService.clear();
        });
    }

    @Test
    public void createGameCorrect(){
        assertDoesNotThrow(()-> {
        var userDataAccess = new MySQLUserDAO();
        var authDataAccess = new MySQLAuthDAO();
        var gameDataAccess = new MySQLGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);


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

        UserDAO userDataAccess; //mySQL
        GameDAO gameDataAccess;
        AuthDAO authDataAccess;

        try {
            DatabaseManager.createDatabase();
            userDataAccess = new MySQLUserDAO(); //mySQL
            gameDataAccess = new MySQLGameDAO();
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            gameDataAccess = new MemoryGameDAO();
            authDataAccess = new MemoryAuthDAO();
        }
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
        assertDoesNotThrow(()-> {
        var userDataAccess = new MySQLUserDAO();
        var authDataAccess = new MySQLAuthDAO();
        var gameDataAccess = new MySQLGameDAO();
        var userService = new UserService(userDataAccess, authDataAccess);
        var gameService = new GameService(gameDataAccess, authDataAccess);


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
        UserDAO userDataAccess; //mySQL
        GameDAO gameDataAccess;
        AuthDAO authDataAccess;

        try {
            DatabaseManager.createDatabase();
            userDataAccess = new MySQLUserDAO(); //mySQL
            gameDataAccess = new MySQLGameDAO();
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            gameDataAccess = new MemoryGameDAO();
            authDataAccess = new MemoryAuthDAO();
        }
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
        UserDAO userDataAccess; //mySQL
        GameDAO gameDataAccess;
        AuthDAO authDataAccess;

        try {
            DatabaseManager.createDatabase();
            userDataAccess = new MySQLUserDAO(); //mySQL
            gameDataAccess = new MySQLGameDAO();
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            gameDataAccess = new MemoryGameDAO();
            authDataAccess = new MemoryAuthDAO();
        }
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
        UserDAO userDataAccess; //mySQL
        GameDAO gameDataAccess;
        AuthDAO authDataAccess;

        try {
            DatabaseManager.createDatabase();
            userDataAccess = new MySQLUserDAO(); //mySQL
            gameDataAccess = new MySQLGameDAO();
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            gameDataAccess = new MemoryGameDAO();
            authDataAccess = new MemoryAuthDAO();
        }
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
        UserDAO userDataAccess; //mySQL
        GameDAO gameDataAccess;
        AuthDAO authDataAccess;

        try {
            DatabaseManager.createDatabase();
            userDataAccess = new MySQLUserDAO(); //mySQL
            gameDataAccess = new MySQLGameDAO();
            authDataAccess = new MySQLAuthDAO();
        }catch(DataAccessException e){
            userDataAccess = new MemoryUserDAO(); //mySQL
            gameDataAccess = new MemoryGameDAO();
            authDataAccess = new MemoryAuthDAO();
        }
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
