package server.websocket;

import com.google.gson.Gson;
import io.javalin.websocket.*;
import websocket.commands.UserGameCommand;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx){
        System.out.println("ws connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx){
        try{
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (command.getCommandType()){
                case CONNECT -> connect(command.getAuthToken(), ctx.session);//method call
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx){
        System.out.println("ws closed");
    }

    private void connect(String username, Session session) throws IOException{
        connections.add(session);
        var message = String.format("connected");
        var notification = new NotificationMessage(message); //
        connections.broadcast(session, notification);
    }

    private void leave(String username, Session session) throws IOException{
        var message = String.format("left");
        var notification = new NotificationMessage(message); //
        connections.broadcast(session, notification);
        connections.remove(session);
    }

    private void resign(String username, Session session) throws IOException{
        var message = String.format("resigned");
        var notification = new NotificationMessage(message); //
        connections.broadcast(session, notification);
        connections.remove(session);
    }

//    private String getUsername(String token){
//        String username = //get user from DAO?
//        return username;
//    }

}
