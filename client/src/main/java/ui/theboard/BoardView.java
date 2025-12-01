package ui.theboard;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class BoardView {

    public static boolean isWhite = false;
    public static final PrintPieces pieces = new PrintPieces();
    private static String team;
    private static ChessBoard board;
    private static Collection<ChessMove> moves;

    public static void run(ChessBoard theBoard, String color, Collection<ChessMove> theMoves) {
        board = theBoard;
        team = color;
        moves = theMoves;
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
        out.print("   ");
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

    public static void resetBoxColor(){
        if(!isWhite){
            isWhite = true;
        }
        else{
            isWhite = false;
        }
    }

    private static void drawCheckers(PrintStream out, ChessBoard board){
        if(team.equals("black")) {
            for(int i = 1; i <= 8; ++i) {
                drawSideCol(out, i - 1);
                for (int j = 8; j >= 1; --j) {
                    ChessPosition pos = new ChessPosition(i, j);
                    if(checkHighlight(out, pos)){

                    }
                    printTeam(out, board, pos);
                    resetBoxColor();
                }
                drawSideCol(out, i - 1);
                out.print(RESET_BG_COLOR);
                out.println();
                resetBoxColor();
            }
        }
        if(team.equals("white")){
            for(int i = 1; i <= 8; ++i) {
                drawSideCol(out, i - 1);
                for (int j = 1; j <= 8; ++j) {
                    ChessPosition pos = new ChessPosition(i, j);
                    printTeam(out, board, pos);
                    resetBoxColor();
                }
                drawSideCol(out, i - 1);
                out.print(RESET_BG_COLOR);
                out.println();
                resetBoxColor();
            }
        }
    }

    private static void printTeam(PrintStream out, ChessBoard board, ChessPosition pos){
        out.print(RESET_TEXT_COLOR);
        if (board.getPiece(pos) == null && isWhite) {
            out.print(SET_BG_COLOR_LIGHT_BLUE);
            out.print(SET_TEXT_COLOR__LIGHT_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) == null && !isWhite) {
            out.print(SET_BG_COLOR_BLUE);
            out.print(SET_TEXT_COLOR_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) != null && isWhite) {
            out.print(SET_BG_COLOR_LIGHT_BLUE);
            pieces.printPlayer(out, board.getPiece(pos));
        }
        if (board.getPiece(pos) != null && !isWhite) {
            out.print(SET_BG_COLOR_BLUE);
            pieces.printPlayer(out, board.getPiece(pos));

        }
    }

    public static String getTeam() {
        return team;
    }

    public static ChessBoard getBoard() {
        return board;
    }

    private static boolean checkHighlight(PrintStream out, ChessPosition pos){
        if (Highlight.checkHighlight(pos, moves)){
            Highlight.highlight(out, board, pos);
            return true;
        }
        return false;
    }
}
