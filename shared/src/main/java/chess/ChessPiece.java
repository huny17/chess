package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public void left(){
        ArrayList<ChessPosition> leftList = new ArrayList<>();
        int increment = 0;
        ChessPosition newPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-increment);
        while(increment >= 0){
            increment += 1;
            newPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-increment);
            leftList.add(newPos);
        }
    }

    public void right(){
        ArrayList<ChessPosition> leftList = new ArrayList<>();
        int increment = 0;
        ChessPosition newPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+increment);
        while(increment < 8){
            increment += 1;
            newPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+increment);
            leftList.add(newPos);
        }
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
