package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.UserData;
import model.request.CreateGameRequest;
import model.request.LoginRequest;
import model.request.LogoutRequest;
import model.request.RegisterRequest;
import model.result.CreateGameResult;
import model.result.LoginResult;
import model.result.RegisterResult;
import services.ClearService;
import services.GameService;
import services.UserService;

import java.util.Map;

public class Server {

    private final Javalin server;
    private UserService userService;
    private GameService gameService;
    private UserDAO userDataAccess;
    private AuthDAO authDataAccess;
    private ClearService clearService;

    public Server() {
        userDataAccess = new MemoryUserDAO();
        authDataAccess = new MemoryAuthDAO();
        userService = new UserService(userDataAccess, authDataAccess);

        server = Javalin.create(config -> config.staticFiles.add("web"));
        /*register*/
        server.post("user", this::registerHandler); //trying to see if can pass given tests
        /*login*/
        server.post("session", this::registerHandler); //can combine Login with Register??
        /*log out*/
        server.delete("session", this::logoutHandler);
        /*list games*/
        server.get("game", this::logout);
        /*create game*/
        server.post("game", this::logout);
        /*join game*/
        server.put("game", this::logout);
        /*clear*/
        server.delete("db", this::clearHandler);
        /*exception*/
        server.exception("GeneralException", this::exceptionHandler);

    }

    //Call User Service

    private void registerHandler(Context ctx) { //created func to be called
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

    private void logoutHandler(Context ctx) { //created func to be called
        var serializer = new Gson();
        String reqJson = ctx.body();
        LogoutRequest req = serializer.fromJson(reqJson, LogoutRequest.class);
        LoginResult res = userService.logout(req);
        ctx.result(serializer.toJson(res));
    }

    //Call Game Service

    private void createGameHandler(Context ctx) { //created func to be called
        var serializer = new Gson(); //Gson = google json
        String reqJson = ctx.body(); //Json string format from request

        CreateGameRequest req = serializer.fromJson(reqJson, CreateGameRequest.class); //serializer = Gson, makes json request from ctx body
        CreateGameResult res = gameService.register(req);
        ctx.result(serializer.toJson(res)); //json response
    }

    //Call Clear Service

    private void clearHandler(Context ctx){
        clearService.clear();
        ctx.result("{}");
    }


    private void exceptionHandler(){

    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
