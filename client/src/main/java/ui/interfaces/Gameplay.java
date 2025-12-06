package ui.interfaces;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import exceptions.GeneralException;
import model.GameData;
import server.ServerFacade;
import ui.theboard.BoardView;
import ui.theboard.Highlight;
import ui.websocket.WebSocketFacade;
import static ui.EscapeSequences.*;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;
    private final MakeMove moveClass = new MakeMove();
    private ChessGame.TeamColor color;
    ChessPiece piece;

    public Gameplay(ServerFacade server, WebSocketFacade ws){
        this.server = server;
        this.ws = ws;
    }

    public String redraw(GameData gameData, String team) throws GeneralException {
        BoardView.run(gameData.chessGame(), team, null);
        String notification = String.format("%s was redrawn", gameData.gameName());
        return SET_TEXT_COLOR_BLUE + notification;
    }

    public String makeMove(GameData gameData, String team, String... params) throws GeneralException {
        if(params.length == 2){
            color = moveClass.assignTeamColor(gameData, team);
            ChessPosition start = inputToPos(params[0]);
            ChessPosition end = inputToPos(params[1]);
            if(moveClass.isPiece(gameData, start)){
                piece = gameData.chessGame().getBoard().getPiece(start);
                ChessMove move = moveClass.checkPromotion(gameData.chessGame(), new ChessMove(start, end, null));
                ws.makeMove(server.getToken(), gameData.gameID(), move);
            }else {
                throw new GeneralException(GeneralException.ExceptionType.invalid, "There is not a piece there");
            }
        }
        else {
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <start position> <end position>");
        }
        return "";
    }

    public String resign(GameData gameData) throws GeneralException {
        if(moveClass.confirm(gameData.chessGame())) {
            ws.resignGame(server.getToken(), gameData.gameID());
        }
        return "";
    }

    public String leave(GameData gameData) throws GeneralException {
        ws.leaveGame(server.getToken(), gameData.gameID());
        return String.format("You left %s", gameData.gameName());
    }

    public String highlight(GameData gameData, String team, String... params) throws GeneralException {
        if (params.length == 1) {
            color = moveClass.assignTeamColor(gameData, team);
            ChessPosition pos = inputToPos(params[0]);
            Highlight.run(pos);
            return String.format(SET_TEXT_COLOR_BLUE + "Moves for %s highlighted", params[0].toUpperCase());
        } else {
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <start position>");
        }
    }

    public ChessPosition inputToPos(String input) throws GeneralException{
        String i = input.toLowerCase();
        char letter = i.charAt(0);
        int row = Character.getNumericValue(i.charAt(1));
        int col = moveClass.colLetterToInt(letter);
        if(col == 0){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Please input col as a letter [A-H]");
        }
        return new ChessPosition(row, col);
    }
}
