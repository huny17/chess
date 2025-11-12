package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class WhiteBoardView {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;
    private static boolean isWhite = false;
    String theBoard;

    public static void run(ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        out.print(SET_TEXT_BOLD);
        drawBorders(out);
        drawCheckers(out, board);
        drawBorders(out);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBorders(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        String[] headers = {"   A", "  B", "   C", "   D", "   E", "  F", "   G", "   H"};
        out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            printBorderText(out, headers[boardCol]);
        }
        out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
        out.print("  ");
        out.print(SET_BG_COLOR_DARK_GREY);
        out.println();
    }

    private static void drawSideCol(PrintStream out, int index) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        String[] cols = {"  1", "  2", "  3", "  4", "  5", "  6", "  7", "  8"};
        printBorderText(out, cols[index]);
        if (index < BOARD_SIZE_IN_SQUARES) {
            out.print("  ");
        }
    }

    private static void printBorderText(PrintStream out, String text){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(text);
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }

    private static void resetColor(){
        if(isWhite == false){
            isWhite = true;
        }
        else{
            isWhite = false;
        }
    }

    private static void drawCheckers(PrintStream out, ChessBoard board){
        for(int i = BOARD_SIZE_IN_SQUARES; i >= 1; --i){
            resetColor();
            drawSideCol(out, i-1);
            for(int j = BOARD_SIZE_IN_SQUARES; j >= 1; --j){
                out.print(RESET_TEXT_COLOR);
                ChessPosition pos =  board.getPos(i,j);
                if(board.getPiece(pos) == null && isWhite){
                    out.print(SET_BG_COLOR_RED);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
                    resetColor();
                    continue;
                }
                if(board.getPiece(pos) == null && !isWhite){
                    out.print(SET_BG_COLOR_BLACK);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
                    resetColor();
                    continue;
                }
                if(board.getPiece(pos) != null && isWhite){
                    out.print(SET_BG_COLOR_RED);
                    printPlayer(out, board.getPiece(pos));
                    resetColor();
                    continue;
                }
                if(board.getPiece(pos) != null && !isWhite){
                    out.print(SET_BG_COLOR_BLACK);
                    printPlayer(out, board.getPiece(pos));
                    resetColor();
                }
            }
            drawSideCol(out, i-1);
            out.print(SET_BG_COLOR_DARK_GREY);
            out.println();
        }
    }

    private static void printPlayer(PrintStream out, ChessPiece piece){
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_BOLD);
            out.print(SET_TEXT_COLOR_WHITE);
            switch (piece.getPieceType()) {
                case KING:
                    out.print(SET_TEXT_COLOR_WHITE);
                    out.print(WHITE_KING);
                    break;
                case QUEEN:
                    out.print(WHITE_QUEEN);
                    break;
                case BISHOP:
                    out.print(WHITE_BISHOP);
                    break;
                case KNIGHT:
                    out.print(WHITE_KNIGHT);
                    break;
                case ROOK:
                    out.print(WHITE_ROOK);
                    break;
                case PAWN:
                    out.print(WHITE_PAWN);
                    break;
            }
        }
        else{
            out.print(SET_TEXT_BOLD);
            out.print(SET_TEXT_COLOR_DARK_GREY);
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

    public String getBoard(){
        return theBoard;
    }

}
