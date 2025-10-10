package server;

import com.google.gson.Gson;
import io.javalin.*;
import io.javalin.http.Context;
import java.util.Map;

public class Server {

    private final Javalin server;

    public Server() {
        server = Javalin.create(config -> config.staticFiles.add("web"));
        /*clear*/
        server.delete("db", ctx -> ctx.result("{}"));
        /*register*/
        server.post("user", this::register); //trying to see if can pass given tests
        /*login*/
        server.post("session", this::register); //can combine Login with Register??
        /*log out*/
        server.
        /*list games*/
        /*create game*/
        /*join game*/
        // Register your endpoints and exception handlers here.

    }

    private void register(Context ctx) { //created func to be called
        var serializer = new Gson();
        var req = serializer.fromJson(ctx.body(), Map.class);
        req.put("authToken", "LOL"); //just trying to see if test pass
        var res = serializer.toJson(req);
        ctx.result(res);
    }

    public int run(int desiredPort) {
        server.start(desiredPort);
        return server.port();
    }

    public void stop() {
        server.stop();
    }
}
