package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard() {
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param pos where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition pos, ChessPiece piece) {
        board[pos.getRow() - 1] [pos.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param pos The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition pos) {
        return board[pos.getRow() - 1] [pos.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        board = new ChessPiece[8][8];
        //pos
        ChessPosition br1 = new ChessPosition(8,1);
        ChessPosition bn1 = new ChessPosition(8,2);
        ChessPosition bb1 = new ChessPosition(8,3);
        ChessPosition bq = new ChessPosition(8,4);
        ChessPosition bk = new ChessPosition(8,5);
        ChessPosition bb2 = new ChessPosition(8,6);
        ChessPosition bn2 = new ChessPosition(8,7);
        ChessPosition br2 = new ChessPosition(8,8);
        ChessPosition bp1 = new ChessPosition(7,1);
        ChessPosition bp2 = new ChessPosition(7,2);
        ChessPosition bp3 = new ChessPosition(7,3);
        ChessPosition bp4 = new ChessPosition(7,4);
        ChessPosition bp5 = new ChessPosition(7,5);
        ChessPosition bp6 = new ChessPosition(7,6);
        ChessPosition bp7 = new ChessPosition(7,7);
        ChessPosition bp8 = new ChessPosition(7,8);
        ChessPosition wr1 = new ChessPosition(1,1);
        ChessPosition wn1 = new ChessPosition(1,2);
        ChessPosition wb1 = new ChessPosition(1,3);
        ChessPosition wq = new ChessPosition(1,4);
        ChessPosition wk = new ChessPosition(1,5);
        ChessPosition wb2 = new ChessPosition(1,6);
        ChessPosition wn2 = new ChessPosition(1,7);
        ChessPosition wr2 = new ChessPosition(1,8);
        ChessPosition wp1 = new ChessPosition(2,1);
        ChessPosition wp2 = new ChessPosition(2,2);
        ChessPosition wp3 = new ChessPosition(2,3);
        ChessPosition wp4 = new ChessPosition(2,4);
        ChessPosition wp5 = new ChessPosition(2,5);
        ChessPosition wp6 = new ChessPosition(2,6);
        ChessPosition wp7 = new ChessPosition(2,7);
        ChessPosition wp8 = new ChessPosition(2,8);
        //pieces
        ChessPiece BR1= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BN1= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BB1= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BQ= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BK= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BB2= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BN2= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BR2= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP1= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP2= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP3= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP4= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP5= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP6= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP7= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece BP8= new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece WR1= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WN1= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WB1= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WQ= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WK= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WB2= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WN2= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WR2= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP1= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP2= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP3= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP4= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP5= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP6= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP7= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece WP8= new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        //add
        addPiece(br1, BR1);
        addPiece(bn1, BN1);
        addPiece(bb1, BB1);
        addPiece(bq, BQ);
        addPiece(bk, BK);
        addPiece(bb2, BB2);
        addPiece(bn2,BN2);
        addPiece(br2, BR2);
        addPiece(bp1, BP1);
        addPiece(bp2, BP2);
        addPiece(bp3, BP3);
        addPiece(bp4, BP4);
        addPiece(bp5, BP5);
        addPiece(bp6, BP6);
        addPiece(bp7, BP7);
        addPiece(bp8, BP8);
        addPiece(wr1, WR1);
        addPiece(wn1, WN1);
        addPiece(wb1, WB1);
        addPiece(wq, WQ);
        addPiece(wk, WK);
        addPiece(wb2, WB2);
        addPiece(wn2, WN2);
        addPiece(wr2, WR2);
        addPiece(wp1, WP1);
        addPiece(wp2, WP2);
        addPiece(wp3, WP3);
        addPiece(wp4, WP4);
        addPiece(wp5, WP5);
        addPiece(wp6, WP6);
        addPiece(wp7, WP7);
        addPiece(wp8, WP8);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}



