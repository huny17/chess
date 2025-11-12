package ui;

import chess.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class ChessBoardUI {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 2;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    String theBoard;

    public static void run(ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawBorders(out);

        drawChessBoard(out);

        drawBorders(out);
        //out.print(SET_BG_COLOR_BLACK);
        //out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawBorders(PrintStream out) {
        setBlack(out);

        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol){
            drawHeader(out, headers[boardCol]);
            if(boardCol < BOARD_SIZE_IN_SQUARES-1){
                out.print(SET_BG_COLOR_DARK_GREY);
                out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
            }
        }
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(EMPTY.repeat(prefixLength));
        printBorderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));

    }

    private static void drawSideCol(PrintStream out, String headerText) {
        int prefixLength = 8;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(EMPTY.repeat(prefixLength));
        printBorderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));

    }

    private static void printBorderText(PrintStream out, String player){
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_RED);
        out.print(player);
        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out){
        for(int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow){
            drawRowOfSquares(out);
            if(boardRow < BOARD_SIZE_IN_SQUARES-1){
                drawHorizontalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out){
        for(int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow){
            for(int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol){
                if(squareRow == SQUARE_SIZE_IN_PADDED_CHARS / 2){
                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS/2;
                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;
                    out.print(EMPTY.repeat(prefixLength));
                    //printPlayer(out, );
                    out.print(EMPTY.repeat(suffixLength));
                }
                else{
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
                }
                if(boardCol < BOARD_SIZE_IN_SQUARES - 1){
                    //setRed(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
                }
                setBlack(out);
            }
            out.println();
        }
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
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out){
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player){
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(player);
        setRed(out);
    }

    public String getBoard(){
        return theBoard;
    }

}
