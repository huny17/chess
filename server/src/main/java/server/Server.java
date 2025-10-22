package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.AuthData;
import model.request.*;
import model.result.*;
import services.*;
import dataaccess.GeneralException;

import java.util.HashMap;
import java.util.Map;

public class Server {

    private final Javalin server;
    private final UserService userService;
    private final GameService gameService;
    private final ClearService clearService;
    private final AuthDAO authDataAccess;

    public Server() {
        UserDAO userDataAccess = new MemoryUserDAO();
        authDataAccess = new MemoryAuthDAO();
        GameDAO gameDataAccess = new MemoryGameDAO();
        userService = new UserService(userDataAccess, authDataAccess);
        gameService = new GameService(gameDataAccess, authDataAccess);
        clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);

        server = Javalin.create(config -> config.staticFiles.add("web"));
        /*register*/
        server.post("user", this::registerHandler);
        /*login*/
        server.post("session", this::registerHandler);
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

    private void logoutHandler(Context ctx){
        if(authorized(ctx)){
            var serializer = new Gson();
            String reqJson = ctx.header("authorization");
            LogoutResult res = userService.logout(reqJson);
            ctx.result(serializer.toJson(res));
        }
    }

    //Call Game Service
    private void createGameHandler(Context ctx) throws GeneralException{
        if(authorized(ctx)) {
            var serializer = new Gson();
            String reqJson = ctx.body();

            CreateGameRequest req = serializer.fromJson(reqJson, CreateGameRequest.class);
            CreateGameResult res = gameService.createGame(req);

            ctx.result(serializer.toJson(res));
        }
    }

    private void joinGameHandler(Context ctx) throws GeneralException{ //created func to be called
        if(authorized(ctx)) {
            var serializer = new Gson();
            String reqJson = ctx.body();
            String authToken = ctx.header("authorization");


            JoinGameRequest req = serializer.fromJson(reqJson, JoinGameRequest.class); //serializer = Gson, makes json request from ctx body
            JoinGameResult res = gameService.joinGame(req, authToken);
            ctx.result(serializer.toJson(res)); //json response
        }
    }

    private void listGamesHandler(Context ctx){ //created func to be called
        if(authorized(ctx)) {
            var serializer = new Gson(); //Gson = google json
//            String reqJson = ctx.body(); //Json string format from request
//
//            ListGamesRequest req = serializer.fromJson(reqJson, ListGamesRequest.class); //serializer = Gson, makes json request from ctx body
            ListGamesResult res = gameService.listGames();
            var res2 = serializer.toJson(res);
            ctx.result(res2); //json response
        }
    }

    //Call Clear Service
    private void clearHandler(Context ctx){
        clearService.clear();
        ctx.result("");
    }


    private void exceptionHandler(GeneralException E, Context ctx){
        var message = new Gson().toJson(Map.of("message", String.format("Error: %s", E.getMessage()), "success", false));
        int exceptionStatus = Integer.parseInt(E.getType());
        ctx.status(exceptionStatus);
        ctx.json(message);
    }

    private boolean authorized(Context ctx) {
        String authToken = ctx.header("authorization");
        if (!authDataAccess.getAuthentications().containsKey(authToken)) {
            ctx.contentType("application/json");
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", "Error: invalid authorization")));
            return false;
        }
        return true;
    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
