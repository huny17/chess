import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {

    private static ServerFacade facade;
    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String url = "\"http://localhost:" + port;
        facade = new ServerFacade(url);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clear() {
        Assertions.assertDoesNotThrow(()->facade.clear());
    }

    @Test
    public void normalRegister() {
        UserData user = new UserData("a", "123", "aa");
        Assertions.assertDoesNotThrow(()->facade.register(user));
    }

    @Test
    public void reRegister(){
        UserData user = new UserData("a", "123", "a@a");
        Assertions.assertDoesNotThrow(()->facade.register(user));
        Assertions.assertThrows(Exception.class, ()->facade.register(user));
    }
}
