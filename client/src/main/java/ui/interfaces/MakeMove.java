package ui.interfaces;

import chess.*;
import exceptions.GeneralException;
import model.GameData;
import java.util.Scanner;
import static ui.EscapeSequences.*;

public class MakeMove {
    private String username;

    public MakeMove(){
    }

    public int colLetterToInt(char input){
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

    public void checkTeam(ChessGame game, ChessPosition pos, ChessGame.TeamColor team) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(!piece.getTeamColor().equals(team)){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "You can only move pieces from your own team");
        }
    }

    public void checkTurn(ChessGame game, ChessPosition pos) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(!piece.getTeamColor().equals(game.getTeamTurn())){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Waiting for opponent to make a move");
        }
    }

    public ChessGame.TeamColor assignTeamColor(GameData game, String team){
        String lowerCaseTeam = team.toLowerCase();
        if (lowerCaseTeam.equals("white")){
            username = game.whiteUsername();
            return ChessGame.TeamColor.WHITE;
        }
        if (lowerCaseTeam.equals("black")){
            username = game.blackUsername();
            return ChessGame.TeamColor.BLACK;
        }
        return null;
    }

    public boolean confirm(){
        System.out.print("Are you sure you want to resign?");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_BLUE);
        result = scanner.nextLine();
        return result.equalsIgnoreCase("yes");
    }

    public String getUsername() {
        return username;
    }
}
