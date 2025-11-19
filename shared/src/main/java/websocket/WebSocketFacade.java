package websocket;

import exceptions.GeneralException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade { //extends Endpoint

    Session session;
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

}
