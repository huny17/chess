package ui.interfaces;

import chess.*;
import exceptions.GeneralException;
import model.GameData;
import server.ServerFacade;
import ui.theboard.BoardView;
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
        if(!piece.getTeamColor().equals(color)){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "You can only move pieces from your own team");
        }
        if(!piece.getTeamColor().equals(game.getTeamTurn())){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Waiting for opponent to make a move");
        }
        return true;
    }

    public void updateMove(GameData data, ChessMove move, ChessGame.TeamColor color) throws GeneralException{
        checkTeam(data.chessGame(), move.getStartPosition(), color);
        try {
            ChessGame game = data.chessGame();
            game.makeMove(move);
            //updategame??
            BoardView.run(game.getBoard(), color.toString().toLowerCase(), null);
        } catch (InvalidMoveException e) {
            throw new GeneralException(GeneralException.ExceptionType.invalid, e.getMessage());
        }

    }
}
