import chess.*;
import ui.ChessBoardUI;

public class Main {
    public static void main(String[] args) {
        //var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        //System.out.println("♕ 240 Chess Client: " + piece);

        ChessBoard b = new ChessBoard();
        b.resetBoard();

       ChessBoardUI.run(b);

       //chessboard.main();

    }
}