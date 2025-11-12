package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class ChessBoardUI {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 2;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    private static boolean isWhite = true;

    String theBoard;

    public static void run(ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawBorders(out);

        drawChessBoard(out, board);

        drawBorders(out);
        //out.print(SET_BG_COLOR_BLACK);
        //out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBorders(PrintStream out) {
        setBlack(out);

        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] cols = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(SET_BG_COLOR_BLACK);
                out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
            }
        }
//        setBlack(out);
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol){
//            drawSideCol(out, cols[boardCol]);
//            if(boardCol < BOARD_SIZE_IN_SQUARES-1){
//                out.print(SET_BG_COLOR_DARK_GREY);
//                out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
//            }
//        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printBorderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));

    }

    private static void drawSideCol(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printBorderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));

    }

    private static void printBorderText(PrintStream out, String player){
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_RED);
        out.print(player);
        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out, ChessBoard board){
        for(int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow){
            drawCheckers(out, board);
            if(boardRow < BOARD_SIZE_IN_SQUARES-1){
                drawHorizontalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawCheckers(PrintStream out, ChessBoard board){
//        for(int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow){
//            for(int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol){
        for(int i = 8; i >= 1; --i){
            for(int j = 8; j >= 1; --j){
                out.print(RESET_TEXT_COLOR);
                ChessPosition pos =  board.getPos(i,j);
                if(board.getPiece(pos) == null && isWhite){
                    setRed(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
                    isWhite = false;
                }
                if(board.getPiece(pos) == null && !isWhite){
                    setGrey(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
                    isWhite = true;
                }
                if(board.getPiece(pos) != null && isWhite){
                    out.print(SET_BG_COLOR_RED);
                    printPlayer(out, board.getPiece(pos));
                    isWhite = false;
                }
                if(board.getPiece(pos) != null && !isWhite){
                    out.print(SET_BG_COLOR_DARK_GREY);
                    printPlayer(out, board.getPiece(pos));
                    isWhite = true;
                }
                //setGrey(out);
            }
            out.println();
        }
    }

    private static void drawCheckersFromBoard(PrintStream out, ChessBoard board) {

    }

    private static void drawHorizontalLine(PrintStream out){
        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES*SQUARE_SIZE_IN_PADDED_CHARS+(BOARD_SIZE_IN_SQUARES-1)*LINE_WIDTH_IN_PADDED_CHARS;
        for(int lineRow = 0; lineRow < LINE_WIDTH_IN_PADDED_CHARS; ++ lineRow){
            setRed(out);
            out.print(EMPTY.repeat(boardSizeInSpaces));
            setBlack(out);
            out.println();
        }
    }

    private static void setRed(PrintStream out){
        out.print(SET_BG_COLOR_RED);
       // out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out){
        out.print(SET_BG_COLOR_BLACK);
        //out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGrey(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREY);
        //out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void printPlayer(PrintStream out, ChessPiece piece){
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
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
