package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.BLACK_BISHOP;
import static ui.EscapeSequences.BLACK_KING;
import static ui.EscapeSequences.BLACK_KNIGHT;
import static ui.EscapeSequences.BLACK_PAWN;
import static ui.EscapeSequences.BLACK_QUEEN;
import static ui.EscapeSequences.BLACK_ROOK;

public class PrintPieces{

    public static void printPlayer(PrintStream out, ChessPiece piece){
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_BOLD);
            out.print(SET_TEXT_COLOR_WHITE);
            switchPlayer(out, piece);
        }
        else{
            out.print(SET_TEXT_BOLD);
            out.print(SET_TEXT_COLOR_DARK_BLUE);
            switchPlayer(out, piece);
        }
    }

    private static void switchPlayer(PrintStream out, ChessPiece piece){
        switch (piece.getPieceType()) {
            case KING:
                out.print(BLACK_KING);
                break;
            case QUEEN:
                out.print(BLACK_QUEEN);
                break;
            case BISHOP:
                out.print(BLACK_BISHOP);
                break;
            case KNIGHT:
                out.print(BLACK_KNIGHT);
                break;
            case ROOK:
                out.print(BLACK_ROOK);
                break;
            case PAWN:
                out.print(BLACK_PAWN);
                break;
        }
    }
}
