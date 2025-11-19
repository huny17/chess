package ui.websocket;

import exceptions.GeneralException;
import websocket.NotificationHandler;
import websocket.commands.UserGameCommand;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import jakarta.websocket.Session;

public class WebSocketFacade { //extends Endpoint

    //Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws GeneralException{
        try{
            url = url.replace("http","ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;
        }catch(URISyntaxException e){
            throw new GeneralException(GeneralException.ExceptionType.server, e.getMessage());
        }
    }

    public void makeConnection(String username, String authToken, Integer gameID) throws GeneralException{
        try{
            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);

        }catch(IOException e){
            throw new GeneralException(GeneralException.ExceptionType.server, e.getMessage());
        }
    }

}
