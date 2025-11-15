package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class BlackBoardView {

    private static boolean isWhite = false;

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
        String[] headers = {CHESS_PIECE+"H ", CHESS_PIECE+"G ", CHESS_PIECE+"F ", CHESS_PIECE+"E ", CHESS_PIECE+"D ", CHESS_PIECE+"C ", CHESS_PIECE+"B ", CHESS_PIECE+"A"};
        out.print(EMPTY);
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            printBorderText(out, headers[boardCol]);
        }
        out.print(EMPTY);
        out.print(EMPTY);
        out.print(RESET_BG_COLOR);
        out.println();
    }

    private static void drawSideCol(PrintStream out, int index) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        String[] cols = {CHESS_PIECE+"1  ", CHESS_PIECE+"2  ", CHESS_PIECE+"3  ", CHESS_PIECE+"4  ", CHESS_PIECE+"5  ", CHESS_PIECE+"6  ", CHESS_PIECE+"7  ", CHESS_PIECE+"8  "};
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
        for(int i = 1; i <= 8; ++i){
            resetBoxColor();
            drawSideCol(out, i-1);
            for(int j = 8; j >= 1; --j){
                out.print(RESET_TEXT_COLOR);
                ChessPosition pos =  board.getPos(i,j);
                if(board.getPiece(pos) == null && isWhite){
                    out.print(SET_BG_COLOR_LIGHT_BLUE);
                    out.print(SET_TEXT_COLOR__LIGHT_BLUE);
                    out.print(EMPTY);
                    //out.print(WHITE_PAWN);
                    resetBoxColor();
                    continue;
                }
                if(board.getPiece(pos) == null && !isWhite){
                    out.print(SET_BG_COLOR_BLUE);
                    out.print(SET_TEXT_COLOR_BLUE);
                    out.print(EMPTY);
                    //out.print(BLACK_PAWN);
                    resetBoxColor();
                    continue;
                }
                if(board.getPiece(pos) != null && isWhite){
                    out.print(SET_BG_COLOR_LIGHT_BLUE);
                    printPlayer(out, board.getPiece(pos));
                    resetBoxColor();
                    continue;
                }
                if(board.getPiece(pos) != null && !isWhite){
                    out.print(SET_BG_COLOR_BLUE);
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
