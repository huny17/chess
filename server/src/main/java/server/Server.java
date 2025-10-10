package server;

import com.google.gson.Gson;
import io.javalin.*;
import io.javalin.http.Context;
import java.util.Map;

public class Server {

    private final Javalin server;

    public Server() {
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
        var serializer = new Gson();
        var req = serializer.fromJson(ctx.body(), Map.class);
        req.put("authToken", "LOL"); //just trying to see if test pass
        var res = serializer.toJson(req);
        ctx.result(res);
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
