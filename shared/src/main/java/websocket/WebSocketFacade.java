package websocket;

import exceptions.GeneralException;

import java.net.URI;

public class WebSocketFacade { //extends Endpoint

    public WebSocketFacade(String url, ) throws GeneralException{
        try{
            url = url.replace("http","ws");
            URI socketURI = new URI(url + "/ws");
        }catch(){
            throw new GeneralException();
        }
    }

}
