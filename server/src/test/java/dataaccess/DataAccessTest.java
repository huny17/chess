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

@BeforeAll
public static void configureDatabase(){
    server = new Server();
    var port = server.run(0);
    fakeServer = new TestServerFacade("localhost", Integer.toString(port));
}

@BeforeEach
public void preClear(){fakeServer.clear();}

@AfterAll
static void stop(){server.stop();}

@Test
 void clear() {
    assertDoesNotThrow(()-> {
     UserDAO ua = new MySQLUserDAO();
         ua.createUser(user);
         assertNotNull(ua.getUser(user.username()));
         ua.clear();
         assertNull(ua.getUser(user.username()));
     });
 }

 @Test
 void createUser() {
     assertDoesNotThrow(()-> {
     UserDAO ua = new MySQLUserDAO();
         ua.createUser(user);
         assertNotNull(ua);
     });
 }

//    @Test
//    void badCreateUser() {
//        //UserDAO da;
//        try {
//            UserDAO da = new MySQLUserDAO();
//            assertThrows(da.createUser(null));
//        }
//    }

 @Test
 void createGame() {
     assertDoesNotThrow(()-> {
        UserDAO ua = new MySQLUserDAO();
        GameDAO ga = new MySQLGameDAO();
        ua.createUser(user);
        ga.createGame("test");
        assertNotNull(ga);
     });
 }

    @Test
    void getGame() {
        assertDoesNotThrow(()-> {
            UserDAO ua = new MySQLUserDAO();
            GameDAO ga = new MySQLGameDAO();
            ua.createUser(user);
            ga.createGame("test");
            assertNotNull(ga.getGame(1));
        });
    }

    @Test
    void getWhiteUsername() {
        assertDoesNotThrow(()-> {
            UserDAO ua = new MySQLUserDAO();
            GameDAO ga = new MySQLGameDAO();
            ua.createUser(user);
            ga.createGame("test");
            ga.updateWhiteTeam("1", joinedWhiteGame);
            assertNotNull(ga.getWhiteUser("1"));
        });
    }

    @Test
    void getBlackUsername() {
        assertDoesNotThrow(()-> {
            UserDAO ua = new MySQLUserDAO();
            GameDAO ga = new MySQLGameDAO();
            ua.createUser(user);
            ga.createGame("test");
            ga.updateBlackTeam("1", joinedBlackGame);
            assertNotNull(ga.getBlackUser("1"));
            assertEquals("joe", ga.getBlackUser("1"));
        });
    }

    @Test
    void listGames() {
        assertDoesNotThrow(()-> {
            UserDAO ua = new MySQLUserDAO();
            GameDAO ga = new MySQLGameDAO();
            ua.createUser(user);
            ga.createGame("test");
            ga.createGame("Aye");
            ga.createGame("yo");
            ga.createGame("123");
            assertNotNull(ga.listGames());
            assertEquals(4, ga.listGames().toArray().length);
        });
    }

}
