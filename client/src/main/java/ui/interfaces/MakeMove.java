package ui.interfaces;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import exceptions.GeneralException;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;

public class MakeMove {
    private final ServerFacade server;
    private final WebSocketFacade ws;
    private ChessGame.TeamColor color;

    public MakeMove(ServerFacade server, WebSocketFacade ws) throws GeneralException{
        this.server = server;
        this.ws = ws;
    }

    public int colLetterToInt(char input)throws GeneralException {
        int num;
        switch (input) {
            case 'a' -> num = 1;
            case 'b' -> num = 2;
            case 'c' -> num = 3;
            case 'd' -> num = 4;
            case 'e' -> num = 5;
            case 'f' -> num = 6;
            case 'g' -> num = 7;
            case 'h' -> num = 8;
            default -> num = 0;
        }
        return num;
    }

    public boolean checkTeam(ChessGame game, ChessPosition pos, ChessGame.TeamColor color) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(piece.getTeamColor().equals(color)){
            return true;
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "You can only move pieces from your own team");
    }
}
