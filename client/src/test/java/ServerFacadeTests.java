import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {

    private static ServerFacade facade;
    private static Server server;
    UserData user = new UserData("d", "123", "d@d");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String url = "http://localhost:" + port;
        facade = new ServerFacade(url);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    void clear() {
        Assertions.assertDoesNotThrow(()->facade.clear());
    }

    @BeforeEach
    @Test
    public void normalRegister() {
        Assertions.assertDoesNotThrow(()->facade.register(user));
    }

    @Test
    public void reRegister(){
        Assertions.assertThrows(Exception.class, ()->facade.register(user));
    }
}
