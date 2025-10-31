package dataaccess;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoff.server.TestServerFacade;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTest {
    private static final UserData user = new UserData("joe", "j@j", "j");
    private static final GameData joinedWhiteGame = new GameData(1, "joe", null, "test", new ChessGame());
    private static final GameData joinedBlackGame = new GameData(1, null, "joe", "test", new ChessGame());
    private static Server server;
    private static TestServerFacade fakeServer;
    private static UserDAO uAccess;
    private static AuthDAO aAccess;
    private static GameDAO gAccess;

@BeforeAll
public static void configureDatabase() throws DataAccessException{
    server = new Server();
    var port = server.run(0);
    fakeServer = new TestServerFacade("localhost", Integer.toString(port));
    uAccess = new MySQLUserDAO();
    aAccess = new MySQLAuthDAO();
    gAccess = new MySQLGameDAO();
}

@BeforeEach
public void preClear(){fakeServer.clear();}

@AfterAll
static void stop(){server.stop();}

@Test
 void clear() {
    assertDoesNotThrow(()-> {
         uAccess.createUser(user);
         assertNotNull(uAccess.getUser(user.username()));
         uAccess.clear();
         assertNull(uAccess.getUser(user.username()));
     });
 }

 @Test
 void createUser() {
     assertDoesNotThrow(()-> {
         uAccess.createUser(user);
         assertNotNull(uAccess);
     });
 }

    @Test
    void injectionDefense() {
    assertDoesNotThrow(()-> {
        UserData injection = new UserData("joe","123","c@c); DROP TABLE user; -- ");
        uAccess.createUser(injection);
        assertNotNull(uAccess);
    });
    }

    //check

 @Test
 void createGame() {
     assertDoesNotThrow(()-> {
        uAccess.createUser(user);
        gAccess.createGame("test");
        assertNotNull(gAccess);
     });
 }

    @Test
    void getGame() {
        assertDoesNotThrow(()-> {
            uAccess.createUser(user);
            gAccess.createGame("test");
            assertNotNull(gAccess.getGame(1));
        });
    }

    @Test
    void getWhiteUsername() {
        assertDoesNotThrow(()-> {
            uAccess.createUser(user);
            gAccess.createGame("test");
            gAccess.updateWhiteTeam("1", joinedWhiteGame);
            assertNotNull(gAccess.getWhiteUser("1"));
        });
    }

    @Test
    void getBlackUsername() {
        assertDoesNotThrow(()-> {
            uAccess.createUser(user);
            gAccess.createGame("test");
            gAccess.updateBlackTeam("1", joinedBlackGame);
            assertNotNull(gAccess.getBlackUser("1"));
            assertEquals("joe", gAccess.getBlackUser("1"));
        });
    }

    @Test
    void listGames() {
        assertDoesNotThrow(()-> {
            uAccess.createUser(user);
            gAccess.createGame("test");
            gAccess.createGame("Aye");
            gAccess.createGame("yo");
            gAccess.createGame("123");
            assertNotNull(gAccess.listGames());
            assertEquals(4, gAccess.listGames().toArray().length);
        });
    }

}
