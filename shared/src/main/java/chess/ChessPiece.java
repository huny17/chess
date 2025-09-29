package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

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

    /** my code */

    public boolean pawnCanMove(int row, int col, ChessBoard b){
        ChessPosition e = new ChessPosition(row, col);
        if(b.getPiece(e) == null){
            return true;
        }
        return false;
    }

    public boolean canMove(int row, int col, ChessBoard b){
        ChessPosition e = new ChessPosition(row, col);
        if(b.getPiece(e) == null){
            return true;
        }
        if(b.getPiece(e).getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    public ChessPosition isPiece(int row, int col, ChessBoard b){
        ChessPosition e = new ChessPosition(row, col);
        if(b.getPiece(e) == null){
            return null;
        }
        if(b.getPiece(e).getTeamColor() != pieceColor){
            return e;
        }
        return null;
    }

    public ChessPosition calcPos (int row, int col, ChessBoard b){
        if(canMove(row, col, b)){
            ChessPosition end = new ChessPosition(row, col);
            return end;
        }
        return null;
    }

    public ChessMove calcMove(ChessPosition start, int row, int col, ChessBoard b){
        if(calcPos(row, col, b) != null) {
            ChessPosition end = calcPos(row, col, b);
            ChessMove m = new ChessMove(start, end, null);
            return m;
        }
        return null;
    }

    public ArrayList<ChessMove> left(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(start.getColumn()-i > 0){
            ChessMove m = calcMove(start, start.getRow(), start.getColumn()-i, board);
            if(isPiece(start.getRow(), start.getColumn()-i, board)!= null){
                list.add(m);
                return list;
            }
            if(m == null){
                return list;

            }
            list.add(m);
            i+=1;
        }
        return list;
    }

    public ArrayList<ChessMove> right(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(start.getColumn()+i <= 8){
            ChessMove m = calcMove(start, start.getRow(), start.getColumn()+i, board);
            if(isPiece(start.getRow(), start.getColumn()+i, board)!= null){
                list.add(m);
                return list;
            }
            if(m == null){
                return list;

            }
            list.add(m);
            i+=1;
        }
        return list;
    }

    public ArrayList<ChessMove> down(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(start.getRow()-i > 0){
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn(), board);
            if(isPiece(start.getRow()-i, start.getColumn(), board)!= null){
                list.add(m);
                return list;
            }
            if(m == null){
                return list;

            }
            list.add(m);
            i+=1;
        }
        return list;
    }

    public ArrayList<ChessMove> up(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(start.getRow()+i <= 8){
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn(), board);
            if(isPiece(start.getRow()+i, start.getColumn(), board)!= null){
                list.add(m);
                return list;
            }
            if(m == null){
                return list;

            }
            list.add(m);
            i+=1;
        }
        return list;
    }
    public ArrayList<ChessMove> diag(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while((start.getRow()+i <= 8) && (start.getColumn()+i <= 8)){
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn()+i, board);
            if(isPiece(start.getRow()+i, start.getColumn()+i, board)!= null){
                list.add(m);
                break;
            }
            if(m == null){
                break;

            }
            list.add(m);
            i+=1;
        }
        i = 1;
        while((start.getRow()-i > 0) && (start.getColumn()-i > 0)){
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn()-i, board);
            if(isPiece(start.getRow()-i, start.getColumn()-i, board)!= null){
                list.add(m);
                break;
            }
            if(m == null){
                break;

            }
            list.add(m);
            i+=1;
        }
        i = 1;
        while((start.getRow()+i <= 8) && (start.getColumn()-i > 0)){
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn()-i, board);
            if(isPiece(start.getRow()+i, start.getColumn()-i, board)!= null){
                list.add(m);
                break;
            }
            if(m == null){
                break;

            }
            list.add(m);
            i+=1;
        }
        i = 1;
        while((start.getRow()-i > 0) && (start.getColumn()+i <= 8)){
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn()+i, board);
            if(isPiece(start.getRow()-i, start.getColumn()+i, board)!= null){
                list.add(m);
                break;
            }
            if(m == null){
                break;

            }
            list.add(m);
            i+=1;
        }
        return list;
    }

    public ArrayList<ChessMove> king(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        if((start.getRow()+i <= 8) && (start.getColumn()+i <= 8)){
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn()+i, board);
            if(isPiece(start.getRow()+i, start.getColumn()+i, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if((start.getRow()-i > 0) && (start.getColumn()-i > 0)){
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn()-i, board);
            if(isPiece(start.getRow()-i, start.getColumn()-i, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if ((start.getRow()+i <= 8) && (start.getColumn()-i > 0)){
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn()-i, board);
            if(isPiece(start.getRow()+i, start.getColumn()-i, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if ((start.getRow()-i > 0) && (start.getColumn()+i <= 8)){
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn()+i, board);
            if(isPiece(start.getRow()-i, start.getColumn()+i, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if (start.getRow()+i <= 8){
            ChessMove m = calcMove(start, start.getRow()+i, start.getColumn(), board);
            if(isPiece(start.getRow()+i, start.getColumn(), board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }

        }
        if (start.getRow()-i > 0){
            ChessMove m = calcMove(start, start.getRow()-i, start.getColumn(), board);
            if(isPiece(start.getRow()-i, start.getColumn(), board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }

        }
        if (start.getColumn()-i > 0){
            ChessMove m = calcMove(start, start.getRow(), start.getColumn()-i, board);
            if(isPiece(start.getRow(), start.getColumn()-i, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }

        }
        if (start.getColumn()+i <= 8){
            ChessMove m = calcMove(start, start.getRow(), start.getColumn()+i, board);
            if(isPiece(start.getRow(), start.getColumn()+i, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }

        }
        return list;
    }

    public ArrayList<ChessMove> knight(ChessPosition start, ChessBoard board){
        ArrayList<ChessMove> list = new ArrayList<>();
        if((start.getRow()+1 <= 8) && (start.getColumn()+2 <= 8)){
            ChessMove m = calcMove(start, start.getRow()+1, start.getColumn()+2, board);
            if(isPiece(start.getRow()+1, start.getColumn()+2, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if((start.getRow()-1 > 0) && (start.getColumn()-2 > 0)){
            ChessMove m = calcMove(start, start.getRow()-1, start.getColumn()-2, board);
            if(isPiece(start.getRow()-1, start.getColumn()-2, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if ((start.getRow()+1 <= 8) && (start.getColumn()-2 > 0)){
            ChessMove m = calcMove(start, start.getRow()+1, start.getColumn()-2, board);
            if(isPiece(start.getRow()+1, start.getColumn()-2, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if ((start.getRow()-1 > 0) && (start.getColumn()+2 <= 8)){
            ChessMove m = calcMove(start, start.getRow()-1, start.getColumn()+2, board);
            if(isPiece(start.getRow()-1, start.getColumn()+2, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if((start.getRow()+2 <= 8) && (start.getColumn()+1 <= 8)){
            ChessMove m = calcMove(start, start.getRow()+2, start.getColumn()+1, board);
            if(isPiece(start.getRow()+2, start.getColumn()+1, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if((start.getRow()-2 > 0) && (start.getColumn()-1 > 0)){
            ChessMove m = calcMove(start, start.getRow()-2, start.getColumn()-1, board);
            if(isPiece(start.getRow()-2, start.getColumn()-1, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if ((start.getRow()+2 <= 8) && (start.getColumn()-1 > 0)){
            ChessMove m = calcMove(start, start.getRow()+2, start.getColumn()-1, board);
            if(isPiece(start.getRow()+2, start.getColumn()-1, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        if ((start.getRow()-2 > 0) && (start.getColumn()+1 <= 8)){
            ChessMove m = calcMove(start, start.getRow()-2, start.getColumn()+1, board);
            if(isPiece(start.getRow()-2, start.getColumn()+1, board)!= null){
                list.add(m);
            }
            else if(m != null){
                list.add(m);
            }
        }
        return list;
    }

    public ArrayList<ChessMove> pawn(ChessPosition start, ChessBoard board) {
        ArrayList<ChessMove> list = new ArrayList<>();
        if(board.getPiece(start).pieceColor == ChessGame.TeamColor.WHITE){
            if (start.getRow()+1 <= 8){
                ChessMove m = calcMove(start, start.getRow()+1, start.getColumn(), board);
                if(m != null && pawnCanMove(start.getRow()+1, start.getColumn(), board)) {
                    if (m.getEndPosition().getRow() == 8) {
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.KNIGHT);
                        list.add(m);
                    }
                    else {
                        list.add(m);
                    }
                }
            }
            if (start.getColumn()+1 <= 8 && start.getRow()+1 <= 8) {
                ChessMove m = calcMove(start, start.getRow()+1, start.getColumn()+1, board);
                if(isPiece(start.getRow()+1, start.getColumn()+1, board)!= null) {
                    if (m.getEndPosition().getRow() == 8) {
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.KNIGHT);
                        list.add(m);
                    }
                    else {
                        list.add(m);
                    }
                }
            }
            if (start.getColumn()-1 > 0 && start.getRow()+1 <= 8) {
                ChessMove m = calcMove(start, start.getRow()+1, start.getColumn()-1, board);
                if(isPiece(start.getRow()+1, start.getColumn()-1, board)!= null) {
                    if (m.getEndPosition().getRow() == 8) {
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.KNIGHT);
                        list.add(m);
                    }
                    else {
                        list.add(m);
                    }
                }
            }
            if(start.getRow() == 2){
                ChessMove m = calcMove(start, start.getRow() + 2, start.getColumn(), board);
                if(m != null && pawnCanMove(start.getRow() + 2, start.getColumn(), board) && !list.isEmpty()) {
                    list.add(m);
                }
            }
        }

        if(board.getPiece(start).pieceColor == ChessGame.TeamColor.BLACK){
            if (start.getRow()-1 > 0){
                ChessMove m = calcMove(start, start.getRow()-1, start.getColumn(), board);
                if(m != null && pawnCanMove(start.getRow()-1, start.getColumn(), board)) {
                    if (m.getEndPosition().getRow() == 1) {
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.KNIGHT);
                        list.add(m);
                    }
                    else {
                        list.add(m);
                    }
                }
            }
            if (start.getColumn()+1 <= 8 && start.getRow()-1 > 0) {
                ChessMove m = calcMove(start, start.getRow()-1, start.getColumn()+1, board);
                if(isPiece(start.getRow()-1, start.getColumn()+1, board)!= null) {
                    if (m.getEndPosition().getRow() == 1) {
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.KNIGHT);
                        list.add(m);
                    }
                    else {
                        list.add(m);
                    }
                }
            }
            if (start.getColumn()-1 > 0 && start.getRow()-1 > 0) {
                ChessMove m = calcMove(start, start.getRow()-1, start.getColumn()-1, board);
                if(isPiece(start.getRow()-1, start.getColumn()-1, board)!= null) {
                    if (m.getEndPosition().getRow() == 1) {
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(m.getStartPosition(), m.getEndPosition(), PieceType.KNIGHT);
                        list.add(m);
                    }
                    else {
                        list.add(m);
                    }
                }
            }
            if(start.getRow() == 7){
                ChessMove m = calcMove(start, start.getRow() - 2, start.getColumn(), board);
                if(m != null && pawnCanMove(start.getRow() - 2, start.getColumn(), board) && (!list.isEmpty())) {
                    list.add(m);
                }
            }
        }
        return list;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(myPosition);

        ArrayList<ChessMove> lList = left(myPosition, board);
        Iterator<ChessMove> l = lList.iterator();
        ArrayList<ChessMove> rList = right(myPosition, board);
        Iterator<ChessMove> r = rList.iterator();
        ArrayList<ChessMove> uList = up(myPosition, board);
        Iterator<ChessMove> u = uList.iterator();
        ArrayList<ChessMove> dList = down(myPosition, board);
        Iterator<ChessMove> d = dList.iterator();
        ArrayList<ChessMove> diList = diag(myPosition, board);
        Iterator<ChessMove> di = diList.iterator();
        ArrayList<ChessMove> kiList = king(myPosition, board);
        Iterator<ChessMove> ki = kiList.iterator();
        ArrayList<ChessMove> kList = knight(myPosition, board);
        Iterator<ChessMove> k = kList.iterator();
        ArrayList<ChessMove> pList = pawn(myPosition, board);
        Iterator<ChessMove> p = pList.iterator();


        switch(piece.getPieceType()){
            case KING:
                while(ki.hasNext()){
                    moves.add(ki.next());
                }
                break;
            case QUEEN:
                while(di.hasNext()){
                    moves.add(di.next());
                }
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
                while(di.hasNext()){
                    moves.add(di.next());
                }
                break;
            case KNIGHT:
                while(k.hasNext()){
                    moves.add(k.next());
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
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}

