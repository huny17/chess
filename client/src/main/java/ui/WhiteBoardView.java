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
        out.println();
        drawBorders(out);
        drawCheckers(out, board);
        drawBorders(out);
        out.print(RESET_BG_COLOR+RESET_TEXT_COLOR+RESET_TEXT_BOLD_FAINT);
        out.println();
    }

    private static void drawBorders(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        String[] headers = {" A ", " B ", " C ", " D ", " E ", " F ", " G ", " H "};
        out.print("   ");
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            printBorderText(out, headers[boardCol]);
        }
        out.print("   ");
        out.print(RESET_BG_COLOR);
        out.println();
    }

    private static void drawSideCol(PrintStream out, int index) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        String[] cols = {" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};
        printBorderText(out, cols[index]);
    }

    private static void printBorderText(PrintStream out, String text){
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(text);
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }

    private static void resetBoxColor(){
        if(isWhite == false){
            isWhite = true;
        }
        else{
            isWhite = false;
        }
    }

    private static void drawCheckers(PrintStream out, ChessBoard board){
        for(int i = BOARD_SIZE_IN_SQUARES; i >= 1; --i){
            resetBoxColor();
            drawSideCol(out, i-1);
            for(int j = BOARD_SIZE_IN_SQUARES; j >= 1; --j){
                out.print(RESET_TEXT_COLOR);
                ChessPosition pos =  board.getPos(i,j);
                if(board.getPiece(pos) == null && isWhite){
                    out.print(SET_BG_COLOR_RED);
                    out.print("   ");
                    resetBoxColor();
                    continue;
                }
                if(board.getPiece(pos) == null && !isWhite){
                    out.print(SET_BG_COLOR_BLACK);
                    out.print("   ");
                    resetBoxColor();
                    continue;
                }
                if(board.getPiece(pos) != null && isWhite){
                    out.print(SET_BG_COLOR_RED);
                    printPlayer(out, board.getPiece(pos));
                    resetBoxColor();
                    continue;
                }
                if(board.getPiece(pos) != null && !isWhite){
                    out.print(SET_BG_COLOR_BLACK);
                    printPlayer(out, board.getPiece(pos));
                    resetBoxColor();
                }
            }
            drawSideCol(out, i-1);
            out.print(RESET_BG_COLOR);
            out.println();
        }
    }

    private static void printPlayer(PrintStream out, ChessPiece piece){
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            out.print(SET_TEXT_BOLD);
            out.print(SET_TEXT_COLOR_WHITE);
            switch (piece.getPieceType()) {
                case KING:
                    out.print(" K ");
                    break;
                case QUEEN:
                    out.print(" Q ");
                    break;
                case BISHOP:
                    out.print(" B ");
                    break;
                case KNIGHT:
                    out.print(" N ");
                    break;
                case ROOK:
                    out.print(" R ");
                    break;
                case PAWN:
                    out.print(" P ");
                    break;
            }
        }
        else{
            out.print(SET_TEXT_BOLD);
            out.print(SET_TEXT_COLOR_DARK_GREY);
            switch (piece.getPieceType()) {
                case KING:
                    out.print(" K ");
                    break;
                case QUEEN:
                    out.print(" Q ");
                    break;
                case BISHOP:
                    out.print(" B ");
                    break;
                case KNIGHT:
                    out.print(" N ");
                    break;
                case ROOK:
                    out.print(" R ");
                    break;
                case PAWN:
                    out.print(" P ");
                    break;
            }
        }
    }

    public String getBoard(){
        return theBoard;
    }

}
