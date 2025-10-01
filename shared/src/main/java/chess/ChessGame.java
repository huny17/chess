package chess;

import java.util.*;


/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard currentBoard = new ChessBoard();
    ChessBoard oldBoard;
    TeamColor turn;
    ChessPosition pos;
    ChessMove theMove;

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

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPiece piece = currentBoard.getPiece(startPosition);

        if(piece == null){
            return null;
        }

        //convert collection to array to iterate and index
        Collection<ChessMove> moves = piece.pieceMoves(currentBoard, startPosition);
        ChessMove[] move = moves.toArray(new ChessMove[0]);
        for (int i=0; i < moves.size(); i++){
            if(moveCheck(move[i], piece.getTeamColor())){
                break;
            }
            list.add(move[i]);
        }

        return list;
    }

    public boolean moveCheck(ChessMove move, TeamColor color){
        oldBoard = new ChessBoard(currentBoard);
        currentBoard.move(move);

        System.out.println(currentBoard);

        if (isInCheck(color)) {
            setBoard(oldBoard);
            return true;
        }
        setBoard(oldBoard);
        return false;
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
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                pos = currentBoard.getPos(i, j);
                ChessPiece piece = currentBoard.getPiece(pos);
                if(piece != null) {
                    if (piece.getTeamColor() != teamColor) {
                        //convert collection to array to iterate and index
                        Collection<ChessMove> moves = piece.pieceMoves(currentBoard, pos);
                        ChessMove[] move = moves.toArray(new ChessMove[0]);
                        for (int k = 0; k < moves.size(); k++) {
                            ChessPiece check = currentBoard.getPiece(move[k].getEndPosition());
                            if (check != null){
                                if (check.getPieceType() == ChessPiece.PieceType.KING) {
                                    return true;
                                }
                            }
                        }
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
        if (validMoves(theMove.getStartPosition()) == null & isInCheck(teamColor)){
            return true;
        }
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
        currentBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        currentBoard.resetBoard();
        return currentBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(theMove, chessGame.theMove) && Objects.equals(currentBoard, chessGame.currentBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theMove, currentBoard);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "theMove=" + theMove +
                ", Board=" + currentBoard +
                '}';
    }
}
