package ui.interfaces;

import chess.*;
import model.GameData;
import java.util.Scanner;
import static chess.ChessPiece.PieceType.*;
import static ui.EscapeSequences.*;

public class MakeMove {

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

    public ChessGame.TeamColor assignTeamColor(GameData game, String team){
        String lowerCaseTeam = team.toLowerCase();
        if (lowerCaseTeam.equals("white")){
            return ChessGame.TeamColor.WHITE;
        }
        if (lowerCaseTeam.equals("black")){
            return ChessGame.TeamColor.BLACK;
        }
        return null;
    }

    public boolean confirm(ChessGame game) {
        if(!game.getIsGameOver()){
            System.out.print("Are you sure you want to resign?");
            Scanner scanner = new Scanner(System.in);
            var result = "";
            System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_BLUE);
            result = scanner.nextLine();
            return result.equalsIgnoreCase("yes");
        }
        System.out.print("The game is already over");
        return false;
    }

    public ChessMove checkPromotion(ChessGame game, ChessMove move){
        if(game.getBoard().getPiece(move.getStartPosition()).getPieceType() == PAWN) {
            if (game.getBoard().getPiece(move.getStartPosition()).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (move.getEndPosition().getRow() == 1) {
                    System.out.println();
                    return new ChessMove(move.getStartPosition(), move.getEndPosition(), askPromo());
                }
            }
            if (game.getBoard().getPiece(move.getStartPosition()).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (move.getEndPosition().getRow() == 8) {
                    return new ChessMove(move.getStartPosition(), move.getEndPosition(), askPromo());
                }
            }
        }
        return move;
    }

    private ChessPiece.PieceType askPromo() {
        System.out.print(SET_TEXT_COLOR_BLUE + "Choose your Pawn's promotion: <QUEEN, BISHOP, KNIGHT, ROOK>");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_BLUE);
        result = scanner.nextLine();
        ChessPiece.PieceType type;
        switch(result.toLowerCase()){
            case "bishop" -> type = BISHOP;
            case "knight" -> type = KNIGHT;
            case "rook" -> type = ROOK;
            default -> type = QUEEN;
        };
        System.out.println(type);
        return type;
    }

    public boolean isPiece(GameData gameData, ChessPosition start) {
        if (gameData.chessGame().getBoard().getPiece(start) == null) {
            return false;
        }
        return true;
    }

}
