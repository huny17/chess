package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;
    ChessBoard board;
    ChessPosition myPosition;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public ArrayList<ChessMove> left(){
        ArrayList<ChessMove> leftList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        ChessPosition endPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1);
        ChessPiece n = board.getPiece(endPos);
        ChessMove m = new ChessMove(startPos, endPos, null);
        while((startPos.getRow() > 0) && (n == null)){
            leftList.add(m);
            startPos = endPos;
            endPos = new ChessPosition(startPos.getRow(), startPos.getColumn()-1);
            n = board.getPiece(endPos);
            m = new ChessMove(startPos, endPos, null);
        }
        return leftList;
    }

    public ArrayList<ChessMove> right(){
        ArrayList<ChessMove> rightList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        ChessPosition endPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);
        ChessPiece n = board.getPiece(endPos);
        ChessMove m = new ChessMove(startPos, endPos, null);
        while((startPos.getRow() <= 8) && (n == null)){
            rightList.add(m);
            startPos = endPos;
            endPos = new ChessPosition(startPos.getRow(), startPos.getColumn()+1);
            n = board.getPiece(endPos);
            m = new ChessMove(startPos, endPos, null);
        }
        return rightList;
    }


    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>(); //array or list of positions

        ChessPiece piece = board.getPiece(myPosition);
        switch (piece.getPieceType()){ //depending on piece type add to list
            case KING:
                break;
            case QUEEN:
                break;
            case BISHOP:
                break;
            case KNIGHT:
                break;
            case ROOK:
                ArrayList<ChessMove> leftList = left();
                ListIterator<ChessMove> l = leftList.listIterator();
                while(l.hasNext()){
                    moves.add(l.next());
                }
                ArrayList<ChessMove> rightList = right();
                ListIterator<ChessMove> r = rightList.listIterator();
                while(r.hasNext()){
                    moves.add(r.next());
                }

                break;
            case PAWN:
//                if() {
//                    moves.add();
//                }
                break;
        }
        return moves;
        //return List.of();
    }
}
