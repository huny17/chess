package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessMove theMove = null;
    ChessBoard theBoard = new ChessBoard();
    ArrayList<ChessMove> whiteMoves;
    ArrayList<ChessMove> blackMoves;
    ChessKing kingW;
    ChessKing kingB;
    TeamColor turn;
    ArrayList<ChessPosition> starts;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public void getWhiteMoves(ChessPosition start){
        ChessPiece piece = theBoard.getPiece(start);
        if(piece != null) {
            if (piece.getTeamColor() == TeamColor.WHITE) {
                //convert collection to array to iterate and index
                Collection<ChessMove> moves = piece.pieceMoves(theBoard, start);
                ChessMove[] move = moves.toArray(new ChessMove[0]);
                for (int i = 0; i < moves.size(); i++) {
                    whiteMoves.add(move[i]);
                }
            }
        }
    }

    public void getBlackMoves(ChessPosition start){
        ChessPiece piece = theBoard.getPiece(start);
        if(piece != null) {
            if (piece.getTeamColor() == TeamColor.BLACK) {
                //convert collection to array to iterate and index
                Collection<ChessMove> moves = piece.pieceMoves(theBoard, start);
                ChessMove[] move = moves.toArray(new ChessMove[0]);
                for (int i = 0; i < moves.size(); i++) {
                    blackMoves.add(move[i]);
                }
            }
        }
    }

    public void populateMoves (ChessPosition start){
        ChessPiece piece = theBoard.getPiece(start);
        if(piece != null){
            getWhiteMoves(start);
            getBlackMoves(start);
        }
    }

    public void getStarts(ChessPosition start){
        ChessPiece piece = theBoard.getPiece(start);
        if(piece != null){
            starts.add(start);
        }
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> list = new ArrayList<>();

        ChessPiece piece = theBoard.getPiece(startPosition);

        if(piece == null){
            return null;
        }

        populateMoves(startPosition);

        //getting kings
        if(piece.getPieceType() == ChessPiece.PieceType.KING) {
            if (piece.getTeamColor() == TeamColor.WHITE) {
                kingW = new ChessKing(piece.getTeamColor(), piece.getPieceType(), startPosition);
            }
            if (piece.getTeamColor() == TeamColor.BLACK) {
                kingB = new ChessKing(piece.getTeamColor(), piece.getPieceType(), startPosition);
            }
        }

        //convert collection to array to iterate and index
        Collection<ChessMove> moves = piece.pieceMoves(theBoard, startPosition);
        ChessMove[] move = moves.toArray(new ChessMove[0]);
        for (int i=0; i < moves.size(); i++){
            if(isInCheck(piece.getTeamColor())){
                break;
            }
            list.add(move[i]);
        }

        return list;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        theMove = move;

        ChessBoard board = getBoard();
        if(validMoves(move.getStartPosition()) != null) {
                board.move(move);
        }
        throw new chess.InvalidMoveException(move.toString());
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if(teamColor == TeamColor.WHITE) {
            if (blackMoves != null){
                for (int i = 0; i < blackMoves.size(); i++) {
                    ChessMove m = blackMoves.get(i);
                    if (m.getEndPosition() == kingW.getStart()) {
                        return true;
                    }
                }
            }
        }
        if (teamColor == TeamColor.BLACK) {
            if(whiteMoves != null) {
                for (int i = 0; i < whiteMoves.size(); i++) {
                    ChessMove m = whiteMoves.get(i);
                    if (m.getEndPosition() == kingB.getStart()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
//        king.pieceMoves(board, )


//        if(validMoves(king.startposition).isempty()) {
//          return true;
//        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (validMoves(theMove.getStartPosition()) == null & !isInCheck(teamColor) & !isInCheckmate(teamColor) ) { //& if no legal moves
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        theBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        theBoard.resetBoard();
        return theBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(theMove, chessGame.theMove) && Objects.equals(theBoard, chessGame.theBoard) && Objects.equals(kingW, chessGame.kingW) && Objects.equals(kingB, chessGame.kingB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theMove, theBoard, kingW, kingB);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "theMove=" + theMove +
                ", theBoard=" + theBoard +
                '}';
    }
}
