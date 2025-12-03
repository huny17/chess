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

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay {

    private final ServerFacade server;
    private final WebSocketFacade ws;
    private ChessGame.TeamColor color;
    private final MakeMove moveClass;
    private String username;
    private ChessPiece piece;

    public Gameplay(ServerFacade server, WebSocketFacade ws) throws GeneralException{
        this.server = server;
        this.ws = ws;
        moveClass = new MakeMove(server, ws);
    }

    public String redraw(GameData gameData, String team) throws GeneralException {
        BoardView.run(gameData.chessGame().getBoard(), team, null);
        String notification = String.format("%s was redrawn", gameData.gameName());
        ws.makeConnection(server.getToken(), gameData.gameID());
        return SET_TEXT_COLOR_BLUE + notification;
    }

    public String makeMove(GameData gameData, String team, String... params) throws GeneralException {
        if(params.length == 2){
            assignTeamColor(gameData, team);
            ChessPosition start = inputToPos(params[0]);
            ChessPosition end = inputToPos(params[1]);
            moveClass.updateMove(gameData, new ChessMove(start, end, null), color); //FIGURE OUT PROMO
            piece = gameData.chessGame().getBoard().getPiece(end);
        }
        else {
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <start position> <end position>");
        }
        return String.format("%s moved %s %s", username, piece, new ChessMove(inputToPos(params[0]), inputToPos(params[1]), null));
    }

    public String resign(GameData gameData, String team) throws GeneralException {
        return null;
    }

    public String leave(GameData gameData, String team) throws GeneralException {
        return null;
    }

    public String highlight(GameData gameData, String team, String... params) throws GeneralException {
        if (params.length == 1) {
            assignTeamColor(gameData, team);
            ChessPosition pos = inputToPos(params[0]);
            moveClass.checkTeam(gameData.chessGame(), pos, color);
            Highlight.run(pos);
            return String.format("Moves for %s highlighted", params[0].toUpperCase());
        } else {
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <start position>");
        }
    }

    public ChessPosition inputToPos(String input) throws GeneralException{
        String i = input.toLowerCase();
        char letter = i.charAt(1);
        int row = Character.getNumericValue(i.charAt(0));
        int col = moveClass.colLetterToInt(letter);
        if(col == 0){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Please input col as a letter [A-H]");
        }
        return new ChessPosition(row, col);
    }

    public void assignTeamColor(GameData game, String team){
        String lowerCaseTeam = team.toLowerCase();
        if (lowerCaseTeam.equals("white")){
            color = ChessGame.TeamColor.WHITE;
            username = game.whiteUsername();
        }
        if (lowerCaseTeam.equals("black")){
            color = ChessGame.TeamColor.BLACK;
            username = game.blackUsername();
        }
    }
}
