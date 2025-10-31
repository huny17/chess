package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    public ChessGame.TeamColor pieceColor;
    public PieceType type;

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

    public boolean canMove(int row, int col, ChessBoard board){
        ChessPosition pos= new ChessPosition(row, col);
        if(board.getPiece(pos) == null || board.getPiece(pos).getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    public ChessPosition isPiece(int row, int col, ChessBoard board){
        ChessPosition pos = new ChessPosition(row, col);
        if(board.getPiece(pos) == null){
            return null;
        }
        if(board.getPiece(pos).getTeamColor() != pieceColor){
            return pos;
        }
        return null;
    }

    public ChessPosition isPieceForPawn(int row, int col, ChessBoard board){
        ChessPosition pos = new ChessPosition(row, col);
        if(board.getPiece(pos) != null){
            return pos;
        }
        return null;
    }

    public ChessPosition makePos(int row, int col, ChessBoard board){
        if(canMove(row, col, board)){
            return new ChessPosition(row, col);
        }
        return null;
    }

    public ChessMove makeMove(ChessPosition start, int row, int col, ChessBoard board){
        if(makePos(row, col, board) != null) {
            ChessPosition end = makePos(row, col, board);
            return new ChessMove(start, end, null);
        }
        return null;
    }

    public ArrayList<ChessMove> pawnCalcAttack(ChessPosition start, ChessBoard board, int rowNumber, int colNumber, ArrayList<ChessMove> list, ChessGame.TeamColor color){
        int row = start.getRow();
        int col = start.getColumn();
        if((row + rowNumber > 0) && (col + colNumber > 0) && (row + rowNumber <= 8) && (col + colNumber <= 8)) {
            ChessMove move = makeMove(start, row + rowNumber, col + colNumber, board);
            if (isPiece(row + rowNumber, col + colNumber, board) != null) {
                if(canPromote(row + rowNumber, color)){
                    return checkPromotion(move, list, color);
                }
                if(move != null) {
                    list.add(move);
                    return list;
                }
            }
        }
        return list;
    }

    public ArrayList<ChessMove> pawnCalcDoubleMove(ChessPosition start, ChessBoard board, int rowNumber, int colNumber, int path,  ArrayList<ChessMove> list, ChessGame.TeamColor color){
        int row = start.getRow();
        int col = start.getColumn();
        if((row + rowNumber > 0) && (col + colNumber > 0) && (row + rowNumber <= 8) && (col + colNumber <= 8)) {
            if (isPieceForPawn(row + rowNumber, col + colNumber, board) == null && isPieceForPawn(row + path, col + colNumber, board) == null) {
                ChessMove move = makeMove(start, row + rowNumber, col + colNumber, board);
                if(canPromote(row + rowNumber, color)){
                    return checkPromotion(move, list, color);
                }
                if(move != null) {
                    list.add(move);
                    return list;
                }
            }
        }
        return list;
    }

        public ArrayList<ChessMove> pawnCalcMove(ChessPosition start, ChessBoard board, int rowNumber, int colNumber,  ArrayList<ChessMove> list, ChessGame.TeamColor color){
        int row = start.getRow();
        int col = start.getColumn();
        if((row + rowNumber > 0) && (col + colNumber > 0) && (row + rowNumber <= 8) && (col + colNumber <= 8)) {
            if (isPiece(row + rowNumber, col + colNumber, board) == null) {
                ChessMove move = makeMove(start, row + rowNumber, col + colNumber, board);
                if(canPromote(row + rowNumber, color)){
                    checkPromotion(move, list, color);
                    return list;
                }
                if(move != null) {
                    list.add(move);
                    return list;
                }
            }
        }
        return list;
    }

    private boolean canPromote(int row, ChessGame.TeamColor color) {
        if((color == ChessGame.TeamColor.WHITE && row == 8) | (color == ChessGame.TeamColor.BLACK && row == 1)){
            return true;
        }
        return false;
    }

    private ArrayList<ChessMove> checkPromotion(ChessMove move,ArrayList<ChessMove> list, ChessGame.TeamColor color) {
        if (color == ChessGame.TeamColor.WHITE && move.getEndPosition().getRow() == 8) {
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.QUEEN);
            list.add(move);
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.BISHOP);
            list.add(move);
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.ROOK);
            list.add(move);
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.KNIGHT);
            list.add(move);
        }
        if (color == ChessGame.TeamColor.BLACK && move.getEndPosition().getRow() == 1) {
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.QUEEN);
            list.add(move);
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.BISHOP);
            list.add(move);
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.ROOK);
            list.add(move);
            move = new ChessMove(move.getStartPosition(), move.getEndPosition(), PieceType.KNIGHT);
            list.add(move);
        }
        return list;
    }

    public ArrayList<ChessMove> calcMove(ChessPosition start, ChessBoard board, int rowNumber, int colNumber, ArrayList<ChessMove> list, String moveMultipleSpaces){
        int row = start.getRow();
        int col = start.getColumn();
        if(moveMultipleSpaces.equals("yes")) {
            while ((row + rowNumber > 0) && (col + colNumber > 0) && (row + rowNumber <= 8) && (col + colNumber <= 8)) {
                ChessMove move = makeMove(start, row + rowNumber, col + colNumber, board);
                if (isPiece(row + rowNumber, col + colNumber, board) != null) {
                    list.add(move);
                    return list;
                }
                if (move == null) {
                    return list;

                }
                list.add(move);
                row = row + rowNumber;
                col = col + colNumber;
            }
        }
        else{
            if((row + rowNumber > 0) && (col + colNumber > 0) && (row + rowNumber <= 8) && (col + colNumber <= 8)) {
                ChessMove move = makeMove(start, row + rowNumber, col + colNumber, board);
                if (isPiece(row + rowNumber, col + colNumber, board) != null) {
                    list.add(move);
                    return list;
                }
                if (move == null) {
                    return list;

                }
                list.add(move);
            }
        }
        return list;
    }

    public ArrayList<ChessMove> rook(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        list = calcMove(start, board, 0, -1, list, "yes");
        list = calcMove(start, board, 0, 1, list, "yes");
        list = calcMove(start, board, -1, 0, list, "yes");
        list = calcMove(start, board, 1, 0, list, "yes");
        return list;
    }

    public ArrayList<ChessMove> bishop(ChessPosition start, ChessBoard board, ArrayList<ChessMove> list){
        list = calcMove(start, board, -1, -1, list, "yes");
        list = calcMove(start, board, 1, 1, list, "yes");
        list = calcMove(start, board, -1, 1, list, "yes");
        list = calcMove(start, board, 1, -1, list, "yes");
        return list;
    }

    public ArrayList<ChessMove> knight(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        list = calcMove(start, board, -1, -2, list, "no");
        list = calcMove(start, board, 1, 2, list, "no");
        list = calcMove(start, board, -1, 2, list, "no");
        list = calcMove(start, board, 1, -2, list, "no");
        list = calcMove(start, board, -2, -1, list, "no");
        list = calcMove(start, board, 2, 1, list, "no");
        list = calcMove(start, board, -2, 1, list, "no");
        list = calcMove(start, board, 2, -1, list, "no");
        return list;
    }

    public ArrayList<ChessMove> king(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        list = calcMove(start, board, -1, -1, list, "no");
        list = calcMove(start, board, -1, 1, list, "no");
        list = calcMove(start, board, -1, 0, list, "no");
        list = calcMove(start, board, 0, -1, list, "no");
        list = calcMove(start, board, 1, -1, list, "no");
        list = calcMove(start, board, 1, 1, list, "no");
        list = calcMove(start, board, 1, 0, list, "no");
        list = calcMove(start, board, 0, 1, list, "no");
        return list;
    }

    public ArrayList<ChessMove> pawn(ChessPosition start, ChessBoard board) {
        ArrayList<ChessMove> list = new ArrayList<>();
        if(board.getPiece(start).pieceColor == ChessGame.TeamColor.WHITE) {
        list = pawnCalcMove(start, board, 1, 0, list, board.getPiece(start).pieceColor);
            if(start.getRow()+1 <= 8 && start.getColumn()+1 <= 8 && isPiece(start.getRow()+1, start.getColumn()+1, board)!= null) {
                list = pawnCalcAttack(start, board, 1, 1, list, board.getPiece(start).pieceColor);
            }
            if(start.getRow()+1 <= 8 && start.getColumn()-1 > 0 && isPiece(start.getRow()+1, start.getColumn()-1, board)!= null) {
                list = pawnCalcAttack(start, board, 1, -1, list, board.getPiece(start).pieceColor);
            }
            if(start.getRow() == 2){
                list = pawnCalcDoubleMove(start, board, 2, 0, 1, list, board.getPiece(start).pieceColor);
            }
        }
        if(board.getPiece(start).pieceColor == ChessGame.TeamColor.BLACK) {
            list = pawnCalcMove(start, board, -1, 0, list, board.getPiece(start).pieceColor);
            if(start.getRow()-1 > 0 && start.getColumn()-1 > 0 && isPiece(start.getRow()-1, start.getColumn()-1, board)!= null) {
                list = pawnCalcAttack(start, board, -1, -1, list, board.getPiece(start).pieceColor);
            }
            if(start.getRow()-1 > 0 && start.getColumn()+1 <= 8 &&  isPiece(start.getRow()-1, start.getColumn()+1, board)!= null) {
                list = pawnCalcAttack(start, board, -1, 1, list, board.getPiece(start).pieceColor);
            }
            if(start.getRow() == 7){
                list = pawnCalcDoubleMove(start, board, -2, 0, -1, list, board.getPiece(start).pieceColor);
            }
        }
        return list;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(myPosition);

        switch(piece.getPieceType()){
            case KING:
                return king(myPosition, board);
            case QUEEN:
                moves = rook(myPosition, board);
                moves = bishop(myPosition, board, moves);
                break;
            case BISHOP:
                return bishop(myPosition, board, moves);
            case KNIGHT:
                return knight(myPosition, board);
            case ROOK:
                return rook(myPosition, board);
            case PAWN:
                return pawn(myPosition, board);
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){ return false;}
        ChessPiece piece = (ChessPiece) o;
        return (pieceColor.equals(piece.pieceColor) && type.equals(piece.type));
    }

    @Override
    public int hashCode() {
        return (43 * pieceColor.hashCode() + type.hashCode());
    }

    @Override
    public String toString() {
        String thePiece = "";
        switch(type) {
            case KING:
                if (pieceColor == ChessGame.TeamColor.BLACK) {
                    thePiece = "k";
                }
                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    thePiece = "K";
                }
                break;
            case QUEEN:
                if (pieceColor == ChessGame.TeamColor.BLACK) {
                    thePiece = "q";
                }
                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    thePiece = "Q";
                }
                break;
            case BISHOP:
                if (pieceColor == ChessGame.TeamColor.BLACK) {
                    thePiece = "b";
                }
                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    thePiece = "B";
                }
                break;
            case KNIGHT:
                if (pieceColor == ChessGame.TeamColor.BLACK) {
                    thePiece = "n";
                }
                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    thePiece = "N";
                }
                break;
            case ROOK:
                if (pieceColor == ChessGame.TeamColor.BLACK) {
                    thePiece = "r";
                }
                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    thePiece = "R";
                }
                break;
            case PAWN:
                if (pieceColor == ChessGame.TeamColor.BLACK) {
                    thePiece = "p";
                }
                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    thePiece = "P";
                }
                break;
        }

        return String.format("%s", thePiece);
    }
}

