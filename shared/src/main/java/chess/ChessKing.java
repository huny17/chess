package chess;

import java.util.ArrayList;

public class ChessKing extends ChessPiece {

    private ChessPosition startPosition;
    private ChessKing BlackKing;
    private ChessKing WhiteKing;

    public ChessKing(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessPosition start) {
        super(pieceColor, type);
        this.startPosition = start;
    }

    public void setKing(){
        if (pieceColor == ChessGame.TeamColor.WHITE){
            WhiteKing = this;
        }
        if (pieceColor == ChessGame.TeamColor.BLACK){
            BlackKing = this;
        }
    }

    public ChessKing getWhite(){
        return WhiteKing;
    }

    public ChessKing getBlack(){
        return BlackKing;
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

    public ArrayList<ChessMove> move(ChessPosition start, ChessBoard board){
        this.startPosition = start;
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

    public ChessPosition getStart(){
        return this.startPosition;
    }

}
