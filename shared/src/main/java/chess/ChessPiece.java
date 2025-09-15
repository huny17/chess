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

    public boolean isPieceHere(ChessPosition pos, ChessBoard board){
        ChessPiece n = board.getPiece(pos);
        if ((n == null) || (n.getTeamColor() != pieceColor)){
            return true;
        }
        return false;
    }

    public ArrayList<ChessMove> left(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> leftList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int increment = 1;
        if (startPos.getColumn()-1 > 0) {
            ChessPosition endPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1);
            if (isPieceHere(endPos, board)) {
                ChessMove m = new ChessMove(startPos, endPos, null);
                while ((startPos.getColumn() - increment > 0)) { //checking using old start pos
                    leftList.add(m);
                    increment += 1;
                    if (startPos.getColumn() - increment > 0) { //checking using new start pos
                        endPos = new ChessPosition(startPos.getRow(), startPos.getColumn() - increment);
                    }
                    m = new ChessMove(startPos, endPos, null);
                }
            }
        }
        return leftList;
    }

    public ArrayList<ChessMove> right(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> rightList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int increment = 1;
        if(startPos.getColumn()+1 <= 8) {
            ChessPosition endPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1);
            if (isPieceHere(endPos, board)) {
                ChessMove m = new ChessMove(startPos, endPos, null);
                while ((startPos.getColumn() + increment <= 8)) {
                    rightList.add(m);
                    increment += 1;
                    if (startPos.getColumn() + increment <= 8) {
                        endPos = new ChessPosition(startPos.getRow(), startPos.getColumn() + increment);
                    }
                    m = new ChessMove(startPos, endPos, null);
                }
            }
        }
        return rightList;
    }

    public ArrayList<ChessMove> up(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> upList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int increment = 1;
        if (startPos.getRow() + 1 <= 8) {
            ChessPosition endPos = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            if (isPieceHere(endPos, board)) {
                ChessMove m = new ChessMove(startPos, endPos, null);
                while (startPos.getRow() + increment <= 8) {
                    upList.add(m);
                    increment += 1;
                    if (startPos.getRow() + increment <= 8) {
                        endPos = new ChessPosition(startPos.getRow() + increment, startPos.getColumn());
                    }
                    m = new ChessMove(startPos, endPos, null);
                }
            }
        }
        return upList;
    }

    public ArrayList<ChessMove> down(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> downList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int increment = 1;
        if (startPos.getRow() - 1 > 0) {
            ChessPosition endPos = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (isPieceHere(endPos, board)) {
                ChessMove m = new ChessMove(startPos, endPos, null);
                while (startPos.getRow() - increment > 0) {
                    downList.add(m);
                    increment += 1;
                    if (startPos.getRow() - increment > 0) {
                        endPos = new ChessPosition(startPos.getRow() - increment, startPos.getColumn());
                    }
                    m = new ChessMove(startPos, endPos, null);
                }
            }
        }
        return downList;
    }

    public ArrayList<ChessMove> diagonal(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> downList = new ArrayList<>();
        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int increment = 1;
        if (startPos.getRow() - 1 > 0 && startPos.getColumn() -1 > 0) {
            ChessPosition endPos = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1);
            //ChessPiece n = board.getPiece(endPos);
            ChessMove m = new ChessMove(startPos, endPos, null);
            while (startPos.getRow() - increment > 0) {
                downList.add(m);
                increment += 1;
                if (startPos.getRow() - increment > 0) {
                    endPos = new ChessPosition(startPos.getRow() - increment, startPos.getColumn() - increment);
                }
                //n = board.getPiece(endPos);
                m = new ChessMove(startPos, endPos, null);
            }
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

        ArrayList<ChessMove> leftList = left(myPosition, board);
        ListIterator<ChessMove> l = leftList.listIterator();
        ArrayList<ChessMove> rightList = right(myPosition, board);
        ListIterator<ChessMove> r = rightList.listIterator();
        ArrayList<ChessMove> upList = up(myPosition, board);
        ListIterator<ChessMove> u = upList.listIterator();
        ArrayList<ChessMove> downList = down(myPosition, board);
        ListIterator<ChessMove> d = downList.listIterator();

        ChessPiece piece = board.getPiece(myPosition);
        switch (piece.getPieceType()){ //depending on piece type add to list
            case KING:
                break;
            case QUEEN:
                while(l.hasNext()){
                    moves.add(l.next());
                }
                while(r.hasNext()){
                    moves.add(r.next());
                }
                while(u.hasNext()){
                    moves.add(u.next());
                }
                while(d.hasNext()){
                    moves.add(d.next());
                }
                break;
            case BISHOP:
                break;
            case KNIGHT:
                break;
            case ROOK:
                while(l.hasNext()){
                    moves.add(l.next());
                }
                while(r.hasNext()){
                    moves.add(r.next());
                }
                while(u.hasNext()){
                    moves.add(u.next());
                }
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
