package ui.theboard;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_BOLD_FAINT;
import static ui.EscapeSequences.RESET_TEXT_COLOR;

public class Highlight {

    static String color;
    static ChessBoard board;

    public static void run(ChessPosition pos) {
        color = BoardView.getTeam();
        board = BoardView.getBoard();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Collection<ChessMove> moves = getMoves(pos);
        out.print(ERASE_SCREEN);
        out.print(SET_TEXT_BOLD);
        out.println();
        BoardView.run(board, color, moves);
        out.print(RESET_BG_COLOR+RESET_TEXT_COLOR+RESET_TEXT_BOLD_FAINT);
        out.println();
    }

    private static Collection<ChessMove> getMoves (ChessPosition pos){
        ChessPiece piece = board.getPiece(pos);
        return piece.pieceMoves(board, pos);
    }

    public static boolean checkHighlight(PrintStream out, ChessPosition pos, Collection<ChessMove> moves){
        if(moves == null){
            return false;
        }
        for(ChessMove move : moves){
            if(pos.equals(move.getStartPosition())){
                highlight(out, board, pos);
                BoardView.resetBoxColor();
                return true;
            }
            if(pos.equals(move.getEndPosition())){
                highlight(out, board, pos);
                BoardView.resetBoxColor();
                return true;
            }
        }
        return false;
    }

    public static void highlight(PrintStream out, ChessBoard board, ChessPosition pos){
        out.print(RESET_TEXT_COLOR);
        if (board.getPiece(pos) == null && BoardView.isWhite) {
            out.print(SET_BG_COLOR_GREEN_YELLOW);
            out.print(SET_TEXT_COLOR_LIGHT_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) == null && !BoardView.isWhite) {
            out.print(SET_BG_COLOR_DARK_YELLOW);
            out.print(SET_TEXT_COLOR_YELLOW);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) != null && BoardView.isWhite) {
            out.print(SET_BG_COLOR_GREEN_YELLOW);
            BoardView.pieces.printPlayer(out, board.getPiece(pos));
        }
        if (board.getPiece(pos) != null && !BoardView.isWhite) {
            out.print(SET_BG_COLOR_DARK_YELLOW);
            BoardView.pieces.printPlayer(out, board.getPiece(pos));

        }
    }



}
