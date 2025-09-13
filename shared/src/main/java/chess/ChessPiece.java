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

    public ArrayList<ChessMove> left(ChessPosition myPosition){
        ArrayList<ChessMove> leftList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        ChessPosition endPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1);
        //ChessPiece n = board.getPiece(endPos);
        ChessMove m = new ChessMove(startPos, endPos, null);
        while ((startPos.getColumn()-1 > 0)) {
            leftList.add(m);
            startPos = endPos;
            if (startPos.getColumn()-1 > 0) {
                endPos = new ChessPosition(startPos.getRow(), startPos.getColumn() - 1);
            }
            //n = board.getPiece(endPos);
            m = new ChessMove(startPos, endPos, null);
        }
        return leftList;
    }

    public ArrayList<ChessMove> right(){
        ArrayList<ChessMove> rightList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        ChessPosition endPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);
        //ChessPiece n = board.getPiece(endPos);
        ChessMove m = new ChessMove(startPos, endPos, null);
        while((startPos.getColumn() <= 8)){
            rightList.add(m);
            startPos = endPos;
            endPos = new ChessPosition(startPos.getRow(), startPos.getColumn()+1);
            //n = board.getPiece(endPos);
            m = new ChessMove(startPos, endPos, null);
        }
        return rightList;
    }

    public ArrayList<ChessMove> up(){
        ArrayList<ChessMove> upList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        ChessPosition endPos = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
        //ChessPiece n = board.getPiece(endPos);
        ChessMove m = new ChessMove(startPos, endPos, null);
        while((startPos.getRow() <= 8)){
            upList.add(m);
            startPos = endPos;
            endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn());
            //n = board.getPiece(endPos);
            m = new ChessMove(startPos, endPos, null);
        }
        return upList;
    }

    public ArrayList<ChessMove> down(){
        ArrayList<ChessMove> downList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        ChessPosition endPos = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
        //ChessPiece n = board.getPiece(endPos);
        ChessMove m = new ChessMove(startPos, endPos, null);
        while((startPos.getRow() > 0)){
            downList.add(m);
            startPos = endPos;
            endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn()+1);
            //n = board.getPiece(endPos);
            m = new ChessMove(startPos, endPos, null);
        }
        return downList;
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
                ArrayList<ChessMove> leftList = left(myPosition);
                ListIterator<ChessMove> l = leftList.listIterator();
                while(l.hasNext()){
                    moves.add(l.next());
                }
                ArrayList<ChessMove> rightList = right();
                ListIterator<ChessMove> r = rightList.listIterator();
                while(r.hasNext()){
                    moves.add(r.next());
                }
                ArrayList<ChessMove> upList = up();
                ListIterator<ChessMove> u = upList.listIterator();
                while(u.hasNext()){
                    moves.add(u.next());
                }
                ArrayList<ChessMove> downList = down();
                ListIterator<ChessMove> d = downList.listIterator();
                while(d.hasNext()){
                    moves.add(d.next());
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
