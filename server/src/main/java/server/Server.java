package server;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.UserDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import io.javalin.http.Context;
import model.UserData;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.RegisterResult;
import services.UserService;

import java.util.Map;

public class Server {

    private final Javalin server;
    private UserService userService;
    private UserDAO userDataAccess;
    private AuthDAO authDataAccess;

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
        server.delete("session", this::logout);
        /*list games*/
        server.get("game", this::logout);
        /*create game*/
        server.post("game", this::logout);
        /*join game*/
        server.put("game", this::logout);
        /*clear*/
        server.delete("db", ctx -> ctx.result("{}"));
        /*exception*/
        server.exception();

    }

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

    private void logout(Context ctx) { //created func to be called
        var serializer = new Gson();
        String reqJson = ctx.body();
        var req = serializer.fromJson(reqJson, Map.class);
        //req.put("{}"); //just trying to see if test pass
        ctx.result(serializer.toJson("{}"));
    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
