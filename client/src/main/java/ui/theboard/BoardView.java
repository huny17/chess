package ui.theboard;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class BoardView {

    public static boolean isWhite = false;
    public static final PrintPieces printPieces = new PrintPieces();
    private static String team;
    private static ChessBoard board;
    private static Collection<ChessMove> moves;
    private static ChessGame game;

    public static void run(ChessGame theGame, String color, Collection<ChessMove> theMoves) {
        board = theGame.getBoard();
        game = theGame;
        team = color;
        moves = theMoves;
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        out.print(SET_TEXT_BOLD);
        out.println();
        drawCheckers(out, board);
        out.print(RESET_BG_COLOR+RESET_TEXT_COLOR+RESET_TEXT_BOLD_FAINT);
    }

    private static void drawBorders(PrintStream out, int index, int end) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        String[] headers = {CHESS_PIECE+"H ", CHESS_PIECE+"G ", CHESS_PIECE+"F ", CHESS_PIECE+"E ", CHESS_PIECE+"D ", CHESS_PIECE+"C ", CHESS_PIECE+"B ", CHESS_PIECE+"A"};
        out.print(EMPTY);
        if(index > end){
            for (int i = index; i >= end; --i) {
                printBorderText(out, headers[i]);
            }
        }
        if(index < end){
            for (int i = index; i <= end; ++i) {
                printBorderText(out, headers[i]);
            }
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
            drawBorders(out, 0, 7);
            for(int i = 1; i <= 8; ++i) {
                drawSideCol(out, i - 1);
                for (int j = 8; j >= 1; --j) {
                    ChessPosition pos = new ChessPosition(i, j);
                    if(Highlight.checkHighlight(out, pos, moves)){
                        continue;
                    }
                    printTeam(out, board, pos);
                    resetBoxColor();
                }
                drawSideCol(out, i - 1);
                out.print(RESET_BG_COLOR);
                out.println();
                resetBoxColor();
            }
            drawBorders(out, 0, 7);
        }
        if(team.equals("white")){
            drawBorders(out, 7, 0);
            for(int i = 8; i >= 1; --i) {
                drawSideCol(out, i - 1);
                for (int j = 1; j <= 8; ++j) {
                    ChessPosition pos = new ChessPosition(i, j);
                    if(Highlight.checkHighlight(out, pos, moves)){
                        continue;
                    }
                    printTeam(out, board, pos);
                    resetBoxColor();
                }
                drawSideCol(out, i - 1);
                out.print(RESET_BG_COLOR);
                out.println();
                resetBoxColor();
            }
            drawBorders(out, 7, 0);
        }
    }

    private static void printTeam(PrintStream out, ChessBoard board, ChessPosition pos){
        out.print(RESET_TEXT_COLOR);
        if (board.getPiece(pos) == null && isWhite) {
            out.print(SET_BG_COLOR_LIGHT_BLUE);
            out.print(SET_TEXT_COLOR_LIGHT_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) == null && !isWhite) {
            out.print(SET_BG_COLOR_BLUE);
            out.print(SET_TEXT_COLOR_BLUE);
            out.print(EMPTY);
        }
        if (board.getPiece(pos) != null && isWhite) {
            out.print(SET_BG_COLOR_LIGHT_BLUE);
            printPieces.printPlayer(out, board.getPiece(pos));
        }
        if (board.getPiece(pos) != null && !isWhite) {
            out.print(SET_BG_COLOR_BLUE);
            printPieces.printPlayer(out, board.getPiece(pos));

        }
    }

    public static String getTeam() {
        return team;
    }

    public static ChessBoard getBoard() {
        return board;
    }

    public static ChessGame getGame() {
        return game;
    }

    public static Collection<ChessMove> getMoves (ChessPosition pos){
        Collection<ChessMove> moves = game.validMoves(pos);
        board = game.getBoard();
        return moves;
    }
}
