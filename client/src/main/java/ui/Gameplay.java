package ui;

import exceptions.GeneralException;
import model.request.LoginRequest;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;

    public Gameplay(ServerFacade server, WebSocketFacade ws) throws GeneralException {
        this.server = server;
        this.ws = ws;
    }

    public String redraw(String... params) throws GeneralException {
        if(params.length == 1){

        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }

    public String makeMove(String... params) throws GeneralException {
        if(params.length == 2){

        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }

    public String resign(String... params) throws GeneralException {
        if(params.length == 1){

        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }

    public String leave(String... params) throws GeneralException {
        if(params.length == 1){

        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }

    public String highlight(String... params) throws GeneralException {
        if(params.length == 1){

        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }
}
