package ui.websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import exceptions.GeneralException;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import websocket.Notification;
import websocket.ServerMessageHandler;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import jakarta.websocket.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

public class WebSocketFacade extends Endpoint{

    Session session;
    ServerMessageHandler serverMessageHandler;

    public WebSocketFacade(String url, ServerMessageHandler serverMessage) throws GeneralException{
        try{
            url = url.replace("http","ws");
            URI socketURI = new URI(url + "/ws");
            this.serverMessageHandler = serverMessage;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message){
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch(serverMessage.getServerMessageType()) {
                        case NOTIFICATION ->{
                            NotificationMessage notification = new Gson().fromJson(message, NotificationMessage.class);
                            serverMessageHandler.notify(notification);
                        }
                        case LOAD_GAME -> {
                            LoadGameMessage loadGame = new Gson().fromJson(message, LoadGameMessage.class);
                            serverMessageHandler.notify(loadGame);
                        }
                        case ERROR -> {
                            ErrorMessage err = new Gson().fromJson(message, ErrorMessage.class);
                            serverMessageHandler.notify(err);
                        }
                    }
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

    public void makeMove(String authToken, Integer gameID, ChessMove move) throws GeneralException{
        try{
            var command = new MakeMoveCommand(authToken, gameID, move);
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
