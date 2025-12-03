package server.websocket;

import com.google.gson.Gson;
import io.javalin.websocket.*;
import websocket.commands.UserGameCommand;
import org.eclipse.jetty.websocket.api.Session;
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
                case CONNECT -> connect(ctx.session);//method call
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx){
        System.out.println("ws closed");
    }

    private void connect(Session session) throws IOException{
        connections.add(session);
        //var message = String.format("");

        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(session, notification);
    }

}
