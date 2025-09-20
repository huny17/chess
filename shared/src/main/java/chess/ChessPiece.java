package chess;

import java.util.*;

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

    public boolean isPiece(ChessPosition pos, ChessBoard board){
        ChessPiece n = board.getPiece(pos);
        if(n == null){
            return false;
        }
        if(n.getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    public ChessPosition canMoveHere(ChessPosition pos, ChessBoard board){
        ChessPiece n = board.getPiece(pos);
        if (n == null){
            return pos;
        }
        if(n.getTeamColor() != pieceColor){
            return pos;
        }
        return null;
    }

    public ChessPosition calcPos(int row, int col, ChessBoard board) {
        ChessPosition end = new ChessPosition(row,col);
        if (canMoveHere(end, board) != null) {
            return end;
        }
        return null;
    }

    public ChessMove calcMove(ChessPosition start, int row, int col, ChessBoard board){
            ChessPosition end = calcPos(row, col, board);
            if (end == null) {
                return null;
            }
        return new ChessMove(start, end, null);
    }

    public ArrayList<ChessMove> left(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int i = 1;
        while(start.getColumn()-i > 0) {
            ChessMove m = calcMove(start, start.getRow(), start.getColumn() - i, board);
            if(m != null) {
                list.add(m);
                i += 1;
                if(isPiece(m.getEndPosition(), board)){
                    return list;
                }
            }
            else {
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> right(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int i = 1;
        while(start.getColumn()+i <= 8) {
            ChessMove m = calcMove(start, start.getRow(), start.getColumn() + i, board);
            if(m != null) {
                list.add(m);
                i += 1;
                if(isPiece(m.getEndPosition(), board)){
                    return list;
                }
            }
            else {
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> up(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int i = 1;
        while(start.getRow()+i <= 8) {
            ChessMove m = calcMove(start, start.getRow() + i, start.getColumn(), board);
            if(m != null) {
                list.add(m);
                i += 1;
                if(isPiece(m.getEndPosition(), board)){
                    return list;
                }
            }
            else {
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> down(ChessPosition myPosition, ChessBoard board) {
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int i = 1;
        while (start.getRow()-i > 0) {
            ChessMove m = calcMove(start, start.getRow() - i, start.getColumn(), board);
            if (m != null) {
                list.add(m);
                i += 1;
                if (isPiece(m.getEndPosition(), board)) {
                    return list;
                }
            } else {
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> diagonal(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        //outerloop;
        int i = 1;
        while (start.getRow() + i <= 8 && start.getColumn() + i <= 8) {
            ChessMove m = calcMove(start, start.getRow() + i, start.getColumn() + i, board);
            if (m != null) {
                list.add(m);
                i += 1;
                if (isPiece(m.getEndPosition(), board)) {
                    break;
                }
            } else {
                break;
            }
        }
        i = 1;
        outerloop:
        while(start.getRow()-i > 0 && start.getColumn()-i > 0) {
            ChessMove m = calcMove(start, start.getRow() - i, start.getColumn() - i, board);
            if(m != null) {
                list.add(m);
                i += 1;
                if(isPiece(m.getEndPosition(), board)){
                    break outerloop;
                }
            }
            else {
                break outerloop;
            }
        }
        i = 1;
        outerloop:
        while(start.getRow()-i > 0 && start.getColumn()+i <= 8) {
            ChessMove m = calcMove(start, start.getRow() - i, start.getColumn() + i, board);
            if(m != null) {
                list.add(m);
                i += 1;
                if(isPiece(m.getEndPosition(), board)){
                    break outerloop;
                }
            }
            else {
                break outerloop;
            }
        }
        i = 1;
        while(start.getRow()+i <= 8 && start.getColumn()-i> 0) {
            ChessMove m = calcMove(start, start.getRow() + i, start.getColumn() - i, board);
            if(m != null) {
                list.add(m);
                i += 1;
                if(isPiece(m.getEndPosition(), board)){
                    return list;
                }
            }
            else {
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> kingMove(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        int i = 1;
        if (start.getColumn()-i > 0) {
            ChessMove m = calcMove(start, start.getRow(), start.getColumn() - i, board);
            if(m != null) {
                list.add(m);
            }
        }
        if (start.getColumn()+i <= 8) {
            ChessMove m = calcMove(start, start.getRow(), start.getColumn() + i, board);
            if(m != null) {
                list.add(m);
            };
        }
        if (start.getRow()+i <=8) {
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn(), board);
            if(m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-i > 0) {
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn(), board);
            if(m != null) {
                list.add(m);
            }
        }
        if (start.getRow() + i <= 8 && start.getColumn() + i <= 8) {
           ChessMove m = calcMove(start, start.getRow() + i, start.getColumn() + i, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-i > 0 && start.getColumn()-i > 0) {
            ChessMove m = calcMove(start, start.getRow() - i, start.getColumn() - i, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-i > 0 && start.getColumn()+i <= 8) {
            ChessMove m = calcMove(start, start.getRow() - i, start.getColumn() + i, board);
            if (m != null) {
                list.add(m);
            }
        }
        if(start.getRow()+i <= 8 && start.getColumn()-i> 0) {
            ChessMove m = calcMove(start, start.getRow() + i, start.getColumn() - i, board);
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    public ArrayList<ChessMove> knightMove(ChessPosition myPosition, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        ChessPosition start = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
        if (start.getRow()+1 <= 8 && start.getColumn()+2 <= 8) {
            ChessMove m = calcMove(start, start.getRow() + 1, start.getColumn() + 2, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-1 > 0 && start.getColumn()-2 > 0) {
            ChessMove m = calcMove(start, start.getRow() - 1, start.getColumn() - 2, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-1 > 0 && start.getColumn()+2 <= 8) {
            ChessMove m = calcMove(start, start.getRow() - 1, start.getColumn() + 2, board);
            if (m != null) {
                list.add(m);
            }
        }
        if(start.getRow()+1 <= 8 && start.getColumn()-2> 0) {
            ChessMove m = calcMove(start, start.getRow() + 1, start.getColumn() - 2, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow() + 2 <= 8 && start.getColumn() + 1 <= 8) {
            ChessMove m = calcMove(start, start.getRow() + 2, start.getColumn() + 1, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-2 > 0 && start.getColumn()-1 > 0) {
            ChessMove m = calcMove(start, start.getRow() - 2, start.getColumn() - 1, board);
            if (m != null) {
                list.add(m);
            }
        }
        if (start.getRow()-2 > 0 && start.getColumn()+1 <= 8) {
            ChessMove m = calcMove(start, start.getRow() - 2, start.getColumn() + 1, board);
            if (m != null) {
                list.add(m);
            }
        }
        if(start.getRow()+2 <= 8 && start.getColumn()-1> 0) {
            ChessMove m = calcMove(start, start.getRow() + 2, start.getColumn() - 1, board);
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

   public ArrayList<ChessMove> pawnMove(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        if(board.getPiece(start).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (start.getRow() + 1 <= 8) {
                ChessMove m = calcMove(start, start.getRow() + 1, start.getColumn(), board);
                ChessPosition end = new ChessPosition(start.getRow() + 1, start.getColumn());
                if (m != null) {
                    if(!isPiece(end,board)) {
                        if (start.getRow() + 1 == 8) {
                            m = new ChessMove(start, end, PieceType.QUEEN);
                            list.add(m);
                            m = new ChessMove(start, end, PieceType.BISHOP);
                            list.add(m);
                            m = new ChessMove(start, end, PieceType.KNIGHT);
                            list.add(m);
                            m = new ChessMove(start, end, PieceType.ROOK);
                            list.add(m);
                        } else {
                            list.add(m);
                        }
                    }
                }
                if((start.getRow() + 1 <= 8) && (start.getColumn() - 1 > 0)) {
                    ChessPosition checkL = new ChessPosition(start.getRow()+1,start.getColumn()-1);
                    if (isPiece(checkL, board)) {
                        m = calcMove(start, start.getRow() + 1, start.getColumn() - 1, board);
                        if (start.getRow() + 1 == 8) {
                            m = new ChessMove(start, checkL, PieceType.QUEEN);
                            list.add(m);
                            m = new ChessMove(start, checkL, PieceType.BISHOP);
                            list.add(m);
                            m = new ChessMove(start, checkL, PieceType.KNIGHT);
                            list.add(m);
                            m = new ChessMove(start, checkL, PieceType.ROOK);
                            list.add(m);
                        } else {
                            list.add(m);
                        }
                    }
                }
                if((start.getRow() + 1 <= 8) && (start.getColumn() + 1 <= 8)){
                    ChessPosition checkR = new ChessPosition(start.getRow() + 1, start.getColumn() + 1);
                    if (isPiece(checkR, board)) {

                        m = calcMove(start, start.getRow() + 1, start.getColumn() + 1, board);
                        if (start.getRow() + 1 == 8) {
                            m = new ChessMove(start, checkR, PieceType.QUEEN);
                            list.add(m);
                            m = new ChessMove(start, checkR, PieceType.BISHOP);
                            list.add(m);
                            m = new ChessMove(start, checkR, PieceType.KNIGHT);
                            list.add(m);
                            m = new ChessMove(start, checkR, PieceType.ROOK);
                            list.add(m);
                        } else {
                            list.add(m);
                        }
                    }
                    if (start.getRow() == 2) {
                        m = calcMove(start, start.getRow() + 2, start.getColumn(), board);
                        if (m != null) {
                            ChessPiece n = board.getPiece(end);
                            ChessPiece j = board.getPiece(m.getEndPosition());
                            if(n == null && j == null) {
                                list.add(m);
                            }
                        }
                    }
                }
            }
        }
       if(board.getPiece(start).getTeamColor() == ChessGame.TeamColor.BLACK) {
           if (start.getRow() - 1 > 0) {
               ChessMove m = calcMove(start, start.getRow() - 1, start.getColumn(), board);
               ChessPosition end = new ChessPosition(start.getRow() - 1, start.getColumn());
               if (m != null) {
                   if(!isPiece(end,board)) {
                       if (start.getRow() - 1 == 1) {
                           m = new ChessMove(start, end, PieceType.QUEEN);
                           list.add(m);
                           m = new ChessMove(start, end, PieceType.BISHOP);
                           list.add(m);
                           m = new ChessMove(start, end, PieceType.KNIGHT);
                           list.add(m);
                           m = new ChessMove(start, end, PieceType.ROOK);
                           list.add(m);
                       } else {
                           list.add(m);
                       }
                   }
               }
               if((start.getRow() - 1 > 0) && (start.getColumn() - 1 > 0)) {
                   ChessPosition checkL = new ChessPosition(start.getRow()-1,start.getColumn()-1);
                   if (isPiece(checkL, board)) {
                       m = calcMove(start, start.getRow() - 1, start.getColumn() - 1, board);
                       if (start.getRow() - 1 == 1) {
                           m = new ChessMove(start, checkL, PieceType.QUEEN);
                           list.add(m);
                           m = new ChessMove(start, checkL, PieceType.BISHOP);
                           list.add(m);
                           m = new ChessMove(start, checkL, PieceType.KNIGHT);
                           list.add(m);
                           m = new ChessMove(start, checkL, PieceType.ROOK);
                           list.add(m);
                       } else {
                           list.add(m);
                       }
                   }
               }
               if((start.getRow() - 1 > 0) && (start.getColumn() + 1 <= 8)) {
                   ChessPosition checkR = new ChessPosition(start.getRow() - 1, start.getColumn() + 1);
                   if (isPiece(checkR, board)) {
                       m = calcMove(start, start.getRow() - 1, start.getColumn() + 1, board);
                       if (start.getRow() - 1 == 1) {
                           m = new ChessMove(start, checkR, PieceType.QUEEN);
                           list.add(m);
                           m = new ChessMove(start, checkR, PieceType.BISHOP);
                           list.add(m);
                           m = new ChessMove(start, checkR, PieceType.KNIGHT);
                           list.add(m);
                           m = new ChessMove(start, checkR, PieceType.ROOK);
                           list.add(m);
                       } else {
                           list.add(m);
                       }
                   }
               }
               if (start.getRow() == 7) {
                   m = calcMove(start, start.getRow() - 2, start.getColumn(), board);
                   if (m != null) {
                       ChessPiece n = board.getPiece(end);
                       ChessPiece j = board.getPiece(m.getEndPosition());
                       if(n == null && j == null) {
                           list.add(m);
                       }
                   }
               }
           }
       }
    return list;
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
        ArrayList<ChessMove> diagList = diagonal(myPosition, board);
        ListIterator<ChessMove> diag = diagList.listIterator();
        ArrayList<ChessMove> kList = kingMove(myPosition, board);
        ListIterator<ChessMove> k = kList.listIterator();
        ArrayList<ChessMove> nList = knightMove(myPosition, board);
        ListIterator<ChessMove> n = nList.listIterator();
        ArrayList<ChessMove> pList = pawnMove(myPosition, board);
        ListIterator<ChessMove> p = pList.listIterator();

        ChessPiece piece = board.getPiece(myPosition);
        switch (piece.getPieceType()){ //depending on piece type add to list
            case KING:
                while(k.hasNext()){
                    moves.add(k.next());
                }
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
                while(diag.hasNext()){
                    moves.add(diag.next());
                }
                break;
            case BISHOP:
                while(diag.hasNext()){
                    moves.add(diag.next());
                }
                break;
            case KNIGHT:
                while(n.hasNext()){
                    moves.add(n.next());
                }
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
                while(p.hasNext()){
                    moves.add(p.next());
                }
                break;
           }
        return moves;
        //return List.of();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type && Objects.equals(board, that.board) && Objects.equals(myPosition, that.myPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type, board, myPosition);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                ", board=" + board +
                ", myPosition=" + myPosition +
                '}';
    }
}
