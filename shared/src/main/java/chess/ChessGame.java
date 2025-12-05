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
    TeamColor turn = TeamColor.WHITE;
    ChessPosition pos;
    boolean isGameOver = false;

    public ChessGame() {
        currentBoard.resetBoard();
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
        getBoard();
        ChessPiece piece = currentBoard.getPiece(startPosition);
        if(piece == null){
            return list;
        }
        Collection<ChessMove> moves = piece.pieceMoves(currentBoard, startPosition);
        for(ChessMove move : moves){
            if(moveCheck(move, piece.getTeamColor())){
                continue;
            }
            list.add(move);
        }
        return list;
    }

    public boolean moveCheck(ChessMove move, TeamColor color){
        oldBoard = new ChessBoard(currentBoard);
        currentBoard.movePiece(move);
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
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if(moves.size() != 0) {
            if(moves.contains(move)) {
                if (currentBoard.getPiece(move.getStartPosition()) != null) {
                    TeamColor color = currentBoard.getPiece(move.getStartPosition()).getTeamColor();
                    if (color != turn) {
                        throw new chess.InvalidMoveException(move.toString());
                    }
                    if (color == TeamColor.WHITE) {
                        setTeamTurn(TeamColor.BLACK);
                    }
                    if (color == TeamColor.BLACK) {
                        setTeamTurn(TeamColor.WHITE);
                    }
                    currentBoard.movePiece(move);
                }
            }
            else {
                throw new chess.InvalidMoveException(move.toString());
            }
        }
        else {
            throw new chess.InvalidMoveException(move.toString());
        }
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                pos = currentBoard.getPos(i, j);
                ChessPiece piece = currentBoard.getPiece(pos);
                if(piece != null) {
                    if (piece.getTeamColor() != teamColor) {
                        Collection<ChessMove> moves = piece.pieceMoves(currentBoard, pos);
                        for(ChessMove move : moves){
                            ChessPiece pieceAtEnd = currentBoard.getPiece(move.getEndPosition());
                            if (pieceAtEnd != null){
                                if (pieceAtEnd.getPieceType() == ChessPiece.PieceType.KING) {
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

    public boolean validateMoves(TeamColor color) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                pos = currentBoard.getPos(i, j);
                ChessPiece piece = currentBoard.getPiece(pos);
                if (piece != null) {
                    if (piece.getTeamColor() == color) {
                        Collection<ChessMove> moves = piece.pieceMoves(currentBoard, pos);
                        for (ChessMove move : moves) {
                            if(!validMoves(move.getStartPosition()).isEmpty()){
                                return true;
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
        if (!validateMoves(teamColor) & isInCheck(teamColor)) {
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
        if (!isInCheck(teamColor) & !isInCheckmate(teamColor) & !validateMoves(teamColor)) { //& if no legal moves
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
        return currentBoard;
    }

    public void setGameOver(){
        isGameOver = true;
    }
    public Boolean getIsGameOver(){
        return isGameOver;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){ return false;}
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(currentBoard, chessGame.currentBoard) && Objects.equals(oldBoard, chessGame.oldBoard) && turn == chessGame.turn && Objects.equals(pos, chessGame.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentBoard, oldBoard, turn, pos, isGameOver);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "currentBoard=" + currentBoard +
                ", oldBoard=" + oldBoard +
                ", turn=" + turn +
                ", pos=" + pos +
                '}';
    }
}
