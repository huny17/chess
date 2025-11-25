package ui.interfaces;

import exceptions.GeneralException;
import model.GameData;
import server.ServerFacade;
import ui.theboard.BoardView;
import ui.websocket.WebSocketFacade;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;

    public Gameplay(ServerFacade server, WebSocketFacade ws){
        this.server = server;
        this.ws = ws;
    }

    public String redraw(GameData gameData, String team, String... params) throws GeneralException {
        if (params.length == 1) {
            BoardView.run(gameData.chessGame().getBoard(), team);
            String notification = String.format("%s redrawn", gameData.gameName());
            ws.makeConnection(server.getToken(), gameData.gameID());
            return SET_TEXT_COLOR_BLUE + notification;
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
