package ui.websocket;

import com.google.gson.Gson;
import exceptions.GeneralException;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import websocket.Notification;
import websocket.NotificationHandler;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import jakarta.websocket.*;

public class WebSocketFacade extends Endpoint{

    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws GeneralException{
        try{
            url = url.replace("http","ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public  void onMessage(String message){
                    Notification notification = new Gson().fromJson(message, Notification.class);
                    notificationHandler.notify(notification);
                }
            });
        }catch(IOException|DeploymentException|URISyntaxException e){
            throw new GeneralException(GeneralException.ExceptionType.server, e.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig){}

    public void makeConnection(String authToken, Integer gameID) throws GeneralException{
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        }catch(IOException e){
            throw new GeneralException(GeneralException.ExceptionType.server, e.getMessage());
        }
    }

    public void leaveGame(String authToken, Integer gameID) throws GeneralException{
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        }catch(IOException e){
            throw new GeneralException(GeneralException.ExceptionType.server, e.getMessage());
        }
    }

    public void resignGame(String authToken, Integer gameID) throws GeneralException{
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        }catch(IOException e){
            throw new GeneralException(GeneralException.ExceptionType.server, e.getMessage());
        }
    }

}
