package ui;

import exceptions.GeneralException;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;

    public Gameplay(ServerFacade server, WebSocketFacade ws) throws GeneralException {
        this.server = server;
        this.ws = ws;
    }
}
