package ui;

import exceptions.GeneralException;
import model.GameData;
import model.request.JoinGameRequest;
import model.request.LoginRequest;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;

    public Gameplay(ServerFacade server, WebSocketFacade ws) throws GeneralException {
        this.server = server;
        this.ws = ws;
    }

    public String redraw(String... params) throws GeneralException {
        if(params.length == 1){
            try{
                int id = Integer.parseInt(params[1]);
                if(!listedGames.containsKey(id)){
                    throw new GeneralException(GeneralException.ExceptionType.invalid, "That game does not exist yet, try another game.");
                }
                GameData findGame = listedGames.get(id);
                if(findGame != null){
                    server.joinGame(new JoinGameRequest(params[0].toUpperCase(), findGame.gameID().toString()));
                    String notification = String.format("You are now playing %s", findGame.gameName());
                    runGame(params[0], findGame);
                    ws.makeConnection(server.getToken(), findGame.gameID());
                    return SET_TEXT_COLOR_BLUE+notification;
                }
            }catch(NumberFormatException ignored){
            }
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
