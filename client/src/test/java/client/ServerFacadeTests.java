package client;

import exceptions.GeneralException;
import model.request.CreateGameRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;

public class ServerFacadeTests {

    private static ServerFacade facade;
    private static Server server;
    RegisterRequest user = new RegisterRequest("d", "123", "d@d");

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

    @BeforeEach
    @Test
    void clear() {
        Assertions.assertDoesNotThrow(()->facade.clear());
    }

    @Test
    public void normalRegister() {
        Assertions.assertDoesNotThrow(()->facade.register(user));
    }

    @Test
    public void reRegister(){
        Assertions.assertThrows(Exception.class, ()->{
            facade.register(user);
            facade.register(user);
        });
    }

    @Test
    public void normalLogout(){
        normalRegister();
        Assertions.assertDoesNotThrow(()->facade.logout());
    }

    @Test
    public void invalidLogout(){
        Assertions.assertThrows(GeneralException.class, ()->facade.logout());
    }

    @Test
    public void normalLogin(){
        normalLogout();
        Assertions.assertDoesNotThrow(()->facade.login(new LoginRequest("d", "123")));
    }

    @Test
    public void wrongPassword(){
        normalLogout();
        Assertions.assertThrows(GeneralException.class, ()->facade.login(new LoginRequest("d", "456")));
    }

    @Test
    public void normalCreateGame(){
        normalRegister();
        Assertions.assertDoesNotThrow(()->facade.createGame(new CreateGameRequest("test")));
    }

    @Test
    public void logoutCreateGame(){
        Assertions.assertThrows(GeneralException.class, ()-> facade.createGame(new CreateGameRequest("test")));
    }

    @Test
    public void normalListGame(){
        normalRegister();
        Assertions.assertDoesNotThrow(()->facade.createGame(new CreateGameRequest("test")));
        Assertions.assertDoesNotThrow(()->facade.createGame(new CreateGameRequest("test2")));
        Assertions.assertDoesNotThrow(()->facade.listGames());
    }

    @Test
    public void logoutListGame(){
        Assertions.assertThrows(GeneralException.class, ()-> facade.listGames());
    }
}
