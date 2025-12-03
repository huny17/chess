package ui.interfaces;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import exceptions.GeneralException;
import model.GameData;
import server.ServerFacade;
import ui.theboard.BoardView;
import ui.theboard.Highlight;
import ui.websocket.WebSocketFacade;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;
    private ChessGame.TeamColor color;

    public Gameplay(ServerFacade server, WebSocketFacade ws){
        this.server = server;
        this.ws = ws;
    }

    public String redraw(GameData gameData, String team) throws GeneralException {
        BoardView.run(gameData.chessGame().getBoard(), team, null);
        String notification = String.format("%s was redrawn", gameData.gameName());
        ws.makeConnection(server.getToken(), gameData.gameID());
        return SET_TEXT_COLOR_BLUE + notification;
    }

    public String makeMove(GameData gameData, String team, String... params) throws GeneralException {
        if(params.length == 2){

        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <start position> <end position>");
    }

    public String resign(GameData gameData, String team) throws GeneralException {
        return null;
    }

    public String leave(GameData gameData, String team) throws GeneralException {
        return null;
    }

    public String highlight(GameData gameData, String team, String... params) throws GeneralException {
        if (params.length == 1) {
            assignTeamColor(team);
            ChessPosition pos = inputToPos(params[0]);
            checkTeam(gameData.chessGame(), pos);
            Highlight.run(pos);
            return String.format("Moves for %s highlighted", params[0]);
        } else {
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <start position>");
        }
    }

    public ChessPosition inputToPos(String input) throws GeneralException{
        String i = input.toLowerCase();
        char letter = i.charAt(1);
        int row = Character.getNumericValue(i.charAt(0));
        int col = colLetterToInt(letter);
        if(col == 0){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Please input col as a letter [A-H]");
        }
        return new ChessPosition(row, col);
    }

    public int colLetterToInt(char input)throws GeneralException{
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

    public boolean checkTeam(ChessGame game, ChessPosition pos) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(piece.getTeamColor().equals(color)){
            return true;
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "You can only move pieces from your own team");
    }

    public void assignTeamColor(String team){
        String lowerCaseTeam = team.toLowerCase();
        if (lowerCaseTeam.equals("white")){
            color = ChessGame.TeamColor.WHITE;
        }
        if (lowerCaseTeam.equals("black")){
            color = ChessGame.TeamColor.BLACK;
        }
    }
}
