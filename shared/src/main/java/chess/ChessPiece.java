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

    public boolean canPawnMove(int r, int c, ChessBoard b){
        if(b.getPiece(new ChessPosition(r,c)) == null){
            return true;
        }
        return false;
    }

    public boolean canMove(int r, int c, ChessBoard b){
        if(b.getPiece(new ChessPosition(r,c)) == null){
            return true;
        }
        if(b.getPiece(new ChessPosition(r,c)).getTeamColor() != pieceColor){
            return true;
        }
        return false;
    }

    private ChessMove calcMove(ChessPosition s, int row, int col, ChessBoard b) {
        if(canMove(row, col, b)){
            ChessPosition e = new ChessPosition(row,col);
            return new ChessMove(s,e,null);
        }
        return null;
    }

    public ArrayList<ChessMove> left(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(s.getColumn()-i > 0){
            ChessMove m = calcMove(s, s.getRow(), s.getColumn()-i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    return list;
                }
                list.add(m);
                i += 1;
            }
            else{
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> right(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(s.getColumn()+i <= 8){
            ChessMove m = calcMove(s, s.getRow(), s.getColumn()+i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    return list;
                }
                list.add(m);
                i += 1;
            }
            else{
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> down(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(s.getRow()-i > 0){
            ChessMove m = calcMove(s, s.getRow()-i, s.getColumn(), b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    return list;
                }
                list.add(m);
                i += 1;
            }
            else{
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> up(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(s.getRow()+i <= 8){
            ChessMove m = calcMove(s, s.getRow()+i, s.getColumn(), b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    return list;
                }
                list.add(m);
                i += 1;
            }
            else{
                return list;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> diag(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        while(s.getRow()+i <= 8 && s.getColumn()+i <= 8){
            ChessMove m = calcMove(s, s.getRow()+i, s.getColumn()+i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    break;
                }
                list.add(m);
                i += 1;
            }
            else{
                break;
            }
        }
        i = 1;
        while(s.getRow()-i > 0 && s.getColumn()-i > 0){
            ChessMove m = calcMove(s, s.getRow()-i, s.getColumn()-i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    break;
                }
                list.add(m);
                i += 1;
            }
            else{
                break;
            }
        }
        i = 1;
        while(s.getRow()-i > 0 && s.getColumn()+i <= 8){
            ChessMove m = calcMove(s, s.getRow()-i, s.getColumn()+i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    break;
                }
                list.add(m);
                i += 1;
            }
            else{
                break;
            }
        }
        i = 1;
        while(s.getRow()+i <= 8 && s.getColumn()-i > 0){
            ChessMove m = calcMove(s, s.getRow()+i, s.getColumn()-i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                    break;
                }
                list.add(m);
                i += 1;
            }
            else{
                break;
            }
        }
        return list;
    }

    public ArrayList<ChessMove> king(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        int i = 1;
        if(s.getRow()+i <= 8 && s.getColumn()+i <= 8){
            ChessMove m = calcMove(s, s.getRow()+i, s.getColumn()+i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-i > 0 && s.getColumn()-i > 0){
            ChessMove m = calcMove(s, s.getRow()-i, s.getColumn()-i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-i > 0 && s.getColumn()+i <= 8){
            ChessMove m = calcMove(s, s.getRow()-i, s.getColumn()+i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()+i <= 8 && s.getColumn()-i > 0){
            ChessMove m = calcMove(s, s.getRow()+i, s.getColumn()-i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getColumn()-i > 0){
            ChessMove m = calcMove(s, s.getRow(), s.getColumn()-i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getColumn()+i <= 8){
            ChessMove m = calcMove(s, s.getRow(), s.getColumn()+i, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-i > 0){
            ChessMove m = calcMove(s, s.getRow()-i, s.getColumn(), b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()+i <= 8){
            ChessMove m = calcMove(s, s.getRow()+i, s.getColumn(), b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        return list;
    }

    public ArrayList<ChessMove> knight(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        if(s.getRow()+1 <= 8 && s.getColumn()+2 <= 8){
            ChessMove m = calcMove(s, s.getRow()+1, s.getColumn()+2, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-1 > 0 && s.getColumn()-2 > 0){
            ChessMove m = calcMove(s, s.getRow()-1, s.getColumn()-2, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-1 > 0 && s.getColumn()+2 <= 8){
            ChessMove m = calcMove(s, s.getRow()-1, s.getColumn()+2, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()+1 <= 8 && s.getColumn()-2 > 0){
            ChessMove m = calcMove(s, s.getRow()+1, s.getColumn()-2, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()+2 <= 8 && s.getColumn()+1 <= 8){
            ChessMove m = calcMove(s, s.getRow()+2, s.getColumn()+1, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-2 > 0 && s.getColumn()-1 > 0){
            ChessMove m = calcMove(s, s.getRow()-2, s.getColumn()-1, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()-2 > 0 && s.getColumn()+1 <= 8){
            ChessMove m = calcMove(s, s.getRow()-2, s.getColumn()+1, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        if(s.getRow()+2 <= 8 && s.getColumn()-1 > 0){
            ChessMove m = calcMove(s, s.getRow()+2, s.getColumn()-1, b);
            if(m != null){
                if(b.getPiece(m.getEndPosition()) != null && b.getPiece(m.getEndPosition()).getTeamColor() != pieceColor){
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
        }
        return list;
    }

    public ArrayList<ChessMove> pawn(ChessPosition s, ChessBoard b){
        ArrayList<ChessMove> list = new ArrayList<>();
        if(pieceColor == ChessGame.TeamColor.WHITE) {
            if (s.getRow() + 1 <= 8 && canPawnMove(s.getRow() + 1, s.getColumn(), b)) {
                ChessMove m = calcMove(s, s.getRow() + 1, s.getColumn(), b);
                if(m.getEndPosition().getRow() == 8){
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.QUEEN);
                    list.add(m);
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.BISHOP);
                    list.add(m);
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.ROOK);
                    list.add(m);
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
            if (s.getRow() + 1 <= 8 && s.getColumn()+1 <= 8 &&
                    b.getPiece(new ChessPosition(s.getRow()+1,s.getColumn()+1)) != null){
                if(b.getPiece(new ChessPosition(s.getRow()+1,s.getColumn()+1)).getTeamColor() != pieceColor) {
                    ChessMove m = calcMove(s, s.getRow() + 1, s.getColumn() + 1, b);
                    if (m.getEndPosition().getRow() == 8) {
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                        list.add(m);
                    } else {
                        list.add(m);
                    }
                }
            }
            if (s.getRow() + 1 <= 8 && s.getColumn()-1 > 0 &&
                    b.getPiece(new ChessPosition(s.getRow()+1,s.getColumn()-1)) != null){
                if(b.getPiece(new ChessPosition(s.getRow()+1,s.getColumn()-1)).getTeamColor() != pieceColor) {
                    ChessMove m = calcMove(s, s.getRow() + 1, s.getColumn() - 1, b);
                    if (m.getEndPosition().getRow() == 8) {
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                        list.add(m);
                    } else {
                        list.add(m);
                    }
                }
            }
            if(s.getRow() == 2){
                if(!list.isEmpty() && canPawnMove(s.getRow() + 2, s.getColumn(), b)){
                    ChessMove m = calcMove(s, s.getRow() + 2, s.getColumn(), b);
                    list.add(m);
                }
            }
        }

        if(pieceColor == ChessGame.TeamColor.BLACK) {
            if (s.getRow() - 1 > 0 && canPawnMove(s.getRow() - 1, s.getColumn(), b)) {
                ChessMove m = calcMove(s, s.getRow() - 1, s.getColumn(), b);
                if(m.getEndPosition().getRow() == 1){
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.QUEEN);
                    list.add(m);
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.BISHOP);
                    list.add(m);
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.ROOK);
                    list.add(m);
                    m = new ChessMove(s,m.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                    list.add(m);
                }
                else {
                    list.add(m);
                }
            }
            if (s.getRow() - 1 > 0 && s.getColumn()+1 <= 8 &&
                    b.getPiece(new ChessPosition(s.getRow()-1,s.getColumn()+1)) != null){
                if(b.getPiece(new ChessPosition(s.getRow()-1,s.getColumn()+1)).getTeamColor() != pieceColor) {
                    ChessMove m = calcMove(s, s.getRow() - 1, s.getColumn() + 1, b);
                    if (m.getEndPosition().getRow() == 1) {
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                        list.add(m);
                    } else {
                        list.add(m);
                    }
                }
            }
            if (s.getRow() - 1 > 0 && s.getColumn()-1 > 0 &&
                    b.getPiece(new ChessPosition(s.getRow()-1,s.getColumn()-1)) != null){
                if(b.getPiece(new ChessPosition(s.getRow()-1,s.getColumn()-1)).getTeamColor() != pieceColor) {
                    ChessMove m = calcMove(s, s.getRow() - 1, s.getColumn() - 1, b);
                    if (m.getEndPosition().getRow() == 1) {
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.QUEEN);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.BISHOP);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.ROOK);
                        list.add(m);
                        m = new ChessMove(s, m.getEndPosition(), ChessPiece.PieceType.KNIGHT);
                        list.add(m);
                    } else {
                        list.add(m);
                    }
                }
            }
            if(s.getRow() == 7){
                if(!list.isEmpty() && canPawnMove(s.getRow() - 2, s.getColumn(), b)){
                    ChessMove m = calcMove(s, s.getRow() - 2, s.getColumn(), b);
                    list.add(m);
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
        ArrayList<ChessMove> moves = new ArrayList<>();

        ArrayList<ChessMove> ll = left(myPosition, board);
        Iterator<ChessMove> l = ll.iterator();
        ArrayList<ChessMove> rl = right(myPosition, board);
        Iterator<ChessMove> r = rl.iterator();
        ArrayList<ChessMove> dl = down(myPosition, board);
        Iterator<ChessMove> d = dl.iterator();
        ArrayList<ChessMove> ul = up(myPosition, board);
        Iterator<ChessMove> u = ul.iterator();
        ArrayList<ChessMove> dil = diag(myPosition, board);
        Iterator<ChessMove> di = dil.iterator();
        ArrayList<ChessMove> kil = king(myPosition, board);
        Iterator<ChessMove> ki = kil.iterator();
        ArrayList<ChessMove> nil = knight(myPosition, board);
        Iterator<ChessMove> ni = nil.iterator();
        ArrayList<ChessMove> pl = pawn(myPosition, board);
        Iterator<ChessMove> p = pl.iterator();

        switch(board.getPiece(myPosition).getPieceType()){
            case ROOK:
                while(l.hasNext()){
                    moves.add(l.next());
                }
                while(r.hasNext()){
                    moves.add(r.next());
                }
                while(d.hasNext()){
                    moves.add(d.next());
                }
                while(u.hasNext()){
                    moves.add(u.next());
                }
                break;
            case BISHOP:
                while(di.hasNext()){
                    moves.add(di.next());
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
                while(d.hasNext()){
                    moves.add(d.next());
                }
                while(u.hasNext()){
                    moves.add(u.next());
                }
                break;
            case KING:
                while(ki.hasNext()){
                    moves.add(ki.next());
                }
                break;
            case KNIGHT:
                while(ni.hasNext()){
                    moves.add(ni.next());
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
