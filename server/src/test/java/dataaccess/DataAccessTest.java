package dataaccess;

import chess.*;
import model.*;
import org.junit.jupiter.api.*;
import passoff.server.TestServerFacade;
import server.Server;
import static org.junit.jupiter.api.Assertions.*;
import Exceptions.*;

class DataAccessTest {
    private static final UserData TEST_USER = new UserData("joe", "j@j", "j");
    private static final GameData JOINED_WHITE_GAME = new GameData(1, "joe", null, "test", new ChessGame());
    private static final GameData JOINED_BLACK_GAME = new GameData(1, null, "joe", "test", new ChessGame());
    private static Server server;
    private static TestServerFacade fakeServer;
    private static UserDAO userAccess;
    private static AuthDAO authAccess;
    private static GameDAO gameAccess;

@BeforeAll
public static void configureDatabase() throws GeneralException{
    server = new Server();
    var port = server.run(0);
    fakeServer = new TestServerFacade("localhost", Integer.toString(port));
    userAccess = new MySQLUserDAO();
    authAccess = new MySQLAuthDAO();
    gameAccess = new MySQLGameDAO();
}

@BeforeEach
public void preClear() throws GeneralException{
    fakeServer.clear();
    userAccess.clear();
    authAccess.clear();
    gameAccess.clear();
}


@AfterAll
static void stop(){server.stop();}

@Test
 void clear() {
    assertDoesNotThrow(()-> {
         userAccess.createUser(TEST_USER);
         assertNotNull(userAccess.getUser(TEST_USER.username()));
         assertNotNull(authAccess);
         authAccess.createAuth(new AuthData("c", "123"));
         gameAccess.createGame("test");
         assertNotNull(gameAccess.getGame(1));
         userAccess.clear();
         gameAccess.clear();
         authAccess.clear();
         assertNull(userAccess.getUser(TEST_USER.username()));
         assertNull(authAccess.getUser("123"));
         assertNull(gameAccess.getGame(1));
     });
 }

 @Test
 void createUser() {
     assertDoesNotThrow(()-> {
         userAccess.createUser(TEST_USER);
         assertNotNull(userAccess);
     });
 }

    @Test
    void longUser() {
        UserData user = new UserData("joe", "123", "some really long thing, not sure how long I officially need to make it but like it needs to be unreasonably long almost like it needs to cause a length issue as was set when the table was made.");
        assertThrows(GeneralException.class, ()-> userAccess.createUser(user));
    }

    @Test
    void userInjectionDefense() {
    assertDoesNotThrow(()-> {
        UserData injection = new UserData("joe","123","c@c); DROP TABLE user; -- ");
        userAccess.createUser(injection);
        assertNotNull(userAccess);
    });
    }

    @Test
    void createAuth() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            assertNotNull(authAccess);
        });
    }

    @Test
    void longAuth(){
            AuthData auth = new AuthData("joe","some really long thing, not sure how long I officially need to make it but like it needs to be unreasonably long almost like it needs to cause a length issue as was set when the table was made.");
            assertThrows(GeneralException.class, ()-> authAccess.createAuth(auth));
    }

    @Test
    void deleteAuth() {
        assertDoesNotThrow(()-> {
            AuthData auth = new AuthData("joe","token");
            authAccess.createAuth(auth);
            assertNotNull(authAccess);
            authAccess.deleteAuth("token");
            assertNotSame(authAccess.getAuth("token"), auth);
        });
    }

    @Test
    void authInjectionDefense() {
        assertDoesNotThrow(()-> {
            AuthData injection = new AuthData("joe","123); DROP TABLE user; -- ");
            authAccess.createAuth(injection);
            assertNotNull(authAccess);
        });
    }

 @Test
 void createGame() {
     assertDoesNotThrow(()-> {
        userAccess.createUser(TEST_USER);
        gameAccess.createGame("test");
        assertNotNull(gameAccess);
     });
 }

    @Test
    void longGameName(){
        assertDoesNotThrow(()->userAccess.createUser(TEST_USER));
        assertThrows(GeneralException.class, ()-> gameAccess.createGame("some really long thing, not sure how long I officially need to make it but like it needs to be unreasonably long almost like it needs to cause a length issue as was set when the table was made."));
    }

    @Test
    void getGame() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            assertNotNull(gameAccess.getGame(1));
        });
    }

    @Test
    void getWhiteUsername() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            gameAccess.updateWhiteTeam("1", JOINED_WHITE_GAME);
            assertNotNull(gameAccess.getWhiteUser("1"));
            gameAccess.createGame("number2");
            assertNull(gameAccess.getWhiteUser("2"));
        });
    }

    @Test
    void getBlackUsername() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            gameAccess.updateBlackTeam("1", JOINED_BLACK_GAME);
            assertNotNull(gameAccess.getBlackUser("1"));
            assertEquals("joe", gameAccess.getBlackUser("1"));
            gameAccess.createGame("number2");
            assertNull(gameAccess.getBlackUser("2"));
        });
    }

    @Test
    void updateGame() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            assertNotNull(gameAccess.getGame(1));
            ChessBoard board = new ChessGame().getBoard();
            ChessGame update = new ChessGame();
            update.setBoard(board);
            update.makeMove(new ChessMove(new ChessPosition(2,3), new ChessPosition(4,3),null));
            GameData updateGame = new GameData(1, null, "joe", "test", update);
            gameAccess.updateGame("1", updateGame);
            GameData theGame = gameAccess.getGame(1);
            assertEquals(theGame.chessGame(), update);
        });
    }

    @Test
    void joinGameBlack() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            assertNotNull(gameAccess.getGame(1));
            GameData updateGame = new GameData(1, null, "joe", "test", new ChessGame());
            gameAccess.updateBlackTeam("1", updateGame);
            GameData theGame = gameAccess.getGame(1);
            assertEquals(theGame.blackUsername(), TEST_USER.username());
        });
    }

    @Test
    void longJoin() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            assertNotNull(gameAccess.getGame(1));
            GameData updateGame = new GameData(1, null, "joeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "test", new ChessGame());
            assertThrows(GeneralException.class, ()-> gameAccess.updateBlackTeam("1", updateGame));
        });
    }

    @Test
    void joinGameWhite() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            assertNotNull(gameAccess.getGame(1));
            GameData updateGame = new GameData(1, "joe", "jill", "test", new ChessGame());
            gameAccess.updateWhiteTeam("1", updateGame);
            GameData theGame = gameAccess.getGame(1);
            assertEquals(theGame.whiteUsername(), TEST_USER.username());
        });
    }

    @Test
    void listGames() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            gameAccess.createGame("test");
            gameAccess.createGame("Aye");
            gameAccess.createGame("yo");
            gameAccess.createGame("123");
            assertNotNull(gameAccess.listGames());
            assertEquals(4, gameAccess.listGames().toArray().length);
        });
    }

    @Test
    void listEmptyGames() {
        assertDoesNotThrow(()-> {
            userAccess.createUser(TEST_USER);
            assertEquals(0, gameAccess.listGames().toArray().length);
        });
    }

}
