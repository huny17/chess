package ui.interfaces;

import chess.*;
import exceptions.GeneralException;
import model.GameData;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;
import java.util.Scanner;
import static ui.EscapeSequences.*;

public class MakeMove {
    private final ServerFacade server;
    private final WebSocketFacade ws;
    private ChessGame.TeamColor color;
    private String username;

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

    public boolean checkTeam(ChessGame game, ChessPosition pos, ChessGame.TeamColor team) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(!piece.getTeamColor().equals(team)){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "You can only move pieces from your own team");
        }
        return true;
    }

    public boolean checkTurn(ChessGame game, ChessPosition pos, ChessGame.TeamColor color) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(!piece.getTeamColor().equals(game.getTeamTurn())){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Waiting for opponent to make a move");
        }
        return true;
    }

    public ChessGame.TeamColor assignTeamColor(GameData game, String team){
        String lowerCaseTeam = team.toLowerCase();
        if (lowerCaseTeam.equals("white")){
            color = ChessGame.TeamColor.WHITE;
            username = game.whiteUsername();
            return color;
        }
        if (lowerCaseTeam.equals("black")){
            color = ChessGame.TeamColor.BLACK;
            username = game.blackUsername();
            return color;
        }
        return null;
    }

    public boolean confirm(){
        System.out.print("Are you sure you want to resign?");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_BLUE);
        result = scanner.nextLine();
        System.out.print(result);
        if(result.equalsIgnoreCase("yes")){
            System.out.println();
            return true;
        }
        System.out.println();
        return false;
    }

    public String getUsername() {
        return username;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }
}
