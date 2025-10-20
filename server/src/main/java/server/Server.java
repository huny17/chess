package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.request.*;
import model.result.*;
import services.*;
import dataaccess.GeneralException;

public class Server {

    private final Javalin server;
    private UserService userService;
    private GameService gameService;
    private UserDAO userDataAccess;
    private AuthDAO authDataAccess;
    private GameDAO gameDataAccess;
    private ClearService clearService;

    public Server() {
        userDataAccess = new MemoryUserDAO();
        authDataAccess = new MemoryAuthDAO();
        gameDataAccess = new MemoryGameDAO();
        userService = new UserService(userDataAccess, authDataAccess);
        gameService = new GameService(gameDataAccess);
        clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);

        server = Javalin.create(config -> config.staticFiles.add("web"));
        /*register*/
        server.post("user", this::registerHandler); //trying to see if can pass given tests
        /*login*/
        server.post("session", this::registerHandler); //can combine Login with Register??
        /*log out*/
        server.delete("session", this::logoutHandler);
        /*list games*/
        server.get("game", this::listGamesHandler);
        /*create game*/
        server.post("game", this::createGameHandler);
        /*join game*/
        server.put("game", this::joinGameHandler);
        /*clear*/
        server.delete("db", this::clearHandler);
        /*exception*/
        server.exception(GeneralException.class, this::exceptionHandler);

    }

    //Call User Service
    private void registerHandler(Context ctx) throws GeneralException{ //created func to be called
        var serializer = new Gson(); //Gson = google json
        String reqJson = ctx.body(); //Json string format from request

        if (reqJson.contains("email")) {
            RegisterRequest req = serializer.fromJson(reqJson, RegisterRequest.class); //serializer = Gson, makes json request from ctx body
            RegisterResult res = userService.register(req);
            ctx.result(serializer.toJson(res)); //json response
        }
        else{
            LoginRequest req = serializer.fromJson(reqJson, LoginRequest.class); //serializer = Gson, makes json request from ctx body
            LoginResult res = userService.login(req);
            ctx.result(serializer.toJson(res));
        }
    }

    private void logoutHandler(Context ctx) throws GeneralException{
        var serializer = new Gson();
        String reqJson = ctx.body();
        LogoutRequest req = serializer.fromJson(reqJson, LogoutRequest.class);
        LogoutResult res = userService.logout(req);
        ctx.result(serializer.toJson(res));
    }

    //Call Game Service
    private void createGameHandler(Context ctx) throws GeneralException{
        var serializer = new Gson();
        String reqJson = ctx.body();

        CreateGameRequest req = serializer.fromJson(reqJson, CreateGameRequest.class);
        CreateGameResult res = gameService.createGame(req);
        ctx.result(serializer.toJson(res));
    }

    private void joinGameHandler(Context ctx) throws GeneralException{ //created func to be called
        var serializer = new Gson();
        String reqJson = ctx.body();

        JoinGameRequest req = serializer.fromJson(reqJson, JoinGameRequest.class); //serializer = Gson, makes json request from ctx body
        JoinGameResult res = gameService.joinGame(req);
        ctx.result(serializer.toJson(res)); //json response
    }

    private void listGamesHandler(Context ctx) throws GeneralException{ //created func to be called
        var serializer = new Gson(); //Gson = google json
        String reqJson = ctx.body(); //Json string format from request

        ListGamesRequest req = serializer.fromJson(reqJson, ListGamesRequest.class); //serializer = Gson, makes json request from ctx body
        ListGamesResult res = gameService.listGames(req);
        ctx.result(serializer.toJson(res)); //json response
    }

    //Call Clear Service
    private void clearHandler(Context ctx){
        clearService.clear();
        ctx.result("");
    }


    private void exceptionHandler(Exception E, Context ctx){

        ctx.result();
    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
