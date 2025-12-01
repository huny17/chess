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

    public static boolean checkHighlight(ChessPosition pos, Collection<ChessMove> moves){
        for(ChessMove move : moves){
            if(pos == move.getStartPosition() || pos == move.getEndPosition()){
                return true;
            }
        }
        return false;
    }

    public static void highlight(PrintStream out, ChessBoard board, ChessPosition pos){
        out.print(RESET_TEXT_COLOR);
        if (board.getPiece(pos) == null && BoardView.isWhite) {
            out.print(SET_BG_COLOR_LIGHT_BLUE);
            out.print(SET_TEXT_COLOR__LIGHT_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) == null && !BoardView.isWhite) {
            out.print(SET_BG_COLOR_BLUE);
            out.print(SET_TEXT_COLOR_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) != null && BoardView.isWhite) {
            out.print(SET_BG_COLOR_LIGHT_BLUE);
            BoardView.pieces.printPlayer(out, board.getPiece(pos));
        }
        if (board.getPiece(pos) != null && !BoardView.isWhite) {
            out.print(SET_BG_COLOR_BLUE);
            BoardView.pieces.printPlayer(out, board.getPiece(pos));

        }
    }



}
