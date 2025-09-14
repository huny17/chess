package chess;

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
        ChessPosition br1 = new ChessPosition(8,1);
        ChessPosition bn1 = new ChessPosition(8,2);
        ChessPosition bb1 = new ChessPosition(8,3);
        ChessPosition bq = new ChessPosition(8,4);
        ChessPosition bk = new ChessPosition(8,5);
        ChessPosition bb2 = new ChessPosition(8,6);
        ChessPosition bk2 = new ChessPosition(8,7);
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
        ChessPosition wk2 = new ChessPosition(1,7);
        ChessPosition wr2 = new ChessPosition(1,8);
        ChessPosition wp1 = new ChessPosition(2,1);
        ChessPosition wp2 = new ChessPosition(2,2);
        ChessPosition wp3 = new ChessPosition(2,3);
        ChessPosition wp4 = new ChessPosition(2,4);
        ChessPosition wp5 = new ChessPosition(2,5);
        ChessPosition wp6 = new ChessPosition(2,6);
        ChessPosition wp7 = new ChessPosition(2,7);
        ChessPosition wp8 = new ChessPosition(2,8);
        board = new ChessPiece[8][8];
//        addPiece(br1, );
//        addPiece(bn1);
//        addPiece(bb1);
//        addPiece(bq);
//        addPiece(bk);
//        addPiece(bb2);
//        addPiece(bk2);
//        addPiece(br2);
//        addPiece(bp1);
//        addPiece(bp2);
//        addPiece(bp3);
//        addPiece(bp4);
//        addPiece(bp5);
//        addPiece(bp6);
//        addPiece(bp7);
//        addPiece(bp8);
//        addPiece(wr1);
//        addPiece(wn1);
//        addPiece(wb1);
//        addPiece(wq);
//        addPiece(wk);
//        addPiece(wb2);
//        addPiece(wk2);
//        addPiece(wr2);
//        addPiece(wp1);
//        addPiece(wp2);
//        addPiece(wp3);
//        addPiece(wp4);
//        addPiece(wp5);
//        addPiece(wp6);
//        addPiece(wp7);
//        addPiece(wp8);
    }
}
