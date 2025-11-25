package ui;

import chess.ChessGame;
import exceptions.GeneralException;
import model.GameData;
import model.request.JoinGameRequest;
import model.request.LoginRequest;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay{

    private final ServerFacade server;
    private final WebSocketFacade ws;

    public Gameplay(ServerFacade server, WebSocketFacade ws) throws GeneralException {
        this.server = server;
        this.ws = ws;
    }

    public String redraw(ChessGame game, String team, String... params) throws GeneralException {
        if(params.length == 1){
                    BlackBoardView.run(game, team);
                    String notification = String.format("You are now playing %s", findGame.gameName());
                    ws.makeConnection(server.getToken(), game.gameID());
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
