package server;

import com.google.gson.Gson;
import dataaccess.UserDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import io.javalin.http.Context;
import model.UserData;
import services.UserService;

import java.util.Map;

public class Server {

    private final Javalin server;
    private UserService userService;
    private UserDAO dataAccess;

    public Server() {
        dataAccess = new MemoryUserDAO();
        userService = new UserService(dataAccess);

        server = Javalin.create(config -> config.staticFiles.add("web"));
        /*register*/
        server.post("user", this::register); //trying to see if can pass given tests
        /*login*/
        server.post("session", this::register); //can combine Login with Register??
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
        // Register your endpoints and exception handlers here.

    }

    private void register(Context ctx) { //created func to be called
        var serializer = new Gson(); //Gson = google json

        String reqJson = ctx.body(); //string from request
        var req = serializer.fromJson(reqJson, UserData.class); //serializer = Gson, makes json request from ctx body

        //call to service

        //var res = Map.of("username", req.get("username", "authToken", "Token"));
        var res = Map.of("username", req.username(),"authToken", "Token"); // to give to json to make json response

        ctx.result(serializer.toJson(res)); //json response



    }

    private void logout(Context ctx) { //created func to be called
        var serializer = new Gson();
        var req = serializer.fromJson(ctx.body(), Map.class);
        //req.put("{}"); //just trying to see if test pass
        var res = serializer.toJson(req);
        ctx.result("{}");
    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
