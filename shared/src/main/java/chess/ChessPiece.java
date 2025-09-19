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
//
//    public ArrayList<ChessMove> kingMove(ChessPosition myPosition, ChessBoard board){
//        ArrayList<ChessMove> kList = new ArrayList<>();
//        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
//        outerloop:
//        if (startPos.getRow() - 1 > 0) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn());
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//                }
//        }
//        outerloop:
//        if (startPos.getRow() + 1 <= 8) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn());
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if (startPos.getColumn() + 1 <= 8) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow(), startPos.getColumn() + 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if (startPos.getColumn() - 1 > 0) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() , startPos.getColumn() - 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() + 1 <= 8) && (startPos.getColumn() - 1 > 0)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() - 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() + 1 <= 8) && (startPos.getColumn() + 1 <= 8)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() + 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null) {
//                    if (board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if (board.getPiece(endPos).getTeamColor() == pieceColor) {
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() - 1 > 0) && (startPos.getColumn() + 1 <= 8)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() + 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                    if (board.getPiece(endPos) != null){
//                        if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                            m = new ChessMove(startPos, endPos, null);
//                            kList.add(m);
//                            break outerloop;
//                        }
//                        if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                            break outerloop;
//                        }
//                    }
//                    m = new ChessMove(startPos, endPos, null);
//                    kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() - 1 > 0) && (startPos.getColumn() - 1 > 0)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() - 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                    if (board.getPiece(endPos) != null){
//                        if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                            m = new ChessMove(startPos, endPos, null);
//                            kList.add(m);
//                            break outerloop;
//                        }
//                        if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                            break outerloop;
//                        }
//                    }
//                    m = new ChessMove(startPos, endPos, null);
//                    kList.add(m);
//            }
//        }
//
//        return kList;
//    }
//
//    public ArrayList<ChessMove> knightMove(ChessPosition myPosition, ChessBoard board){
//        ArrayList<ChessMove> kList = new ArrayList<>();
//        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
//        outerloop:
//        if ((startPos.getRow() - 1 > 0) && (startPos.getColumn() - 2 > 0)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() - 2);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if (((startPos.getRow() - 2 > 0) && (startPos.getColumn() - 1 > 0))) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 2, startPos.getColumn() - 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if (((startPos.getRow() - 1 > 0) && (startPos.getColumn() + 2 <= 8))) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() + 2);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() - 2 > 0 ) && (startPos.getColumn() + 1 <= 8)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() - 2, startPos.getColumn() + 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() + 1 <= 8) && (startPos.getColumn() - 2 > 0)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() - 2);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() + 2 <= 8) && (startPos.getColumn() - 1 > 0)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 2, startPos.getColumn() - 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null) {
//                    if (board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if (board.getPiece(endPos).getTeamColor() == pieceColor) {
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() + 1 <= 8) && (startPos.getColumn() + 2 <= 8)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() + 2);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//        outerloop:
//        if ((startPos.getRow() + 2 <= 8) && (startPos.getColumn() + 1 <= 8)) {
//            ChessPosition endPos = new ChessPosition(startPos.getRow() + 2, startPos.getColumn() + 1);
//            if (isPieceHere(endPos, board)) {
//                ChessMove m = new ChessMove(startPos, endPos, null);
//                if (board.getPiece(endPos) != null){
//                    if(board.getPiece(endPos).getTeamColor() != pieceColor) {
//                        m = new ChessMove(startPos, endPos, null);
//                        kList.add(m);
//                        break outerloop;
//                    }
//                    if(board.getPiece(endPos).getTeamColor() == pieceColor){
//                        break outerloop;
//                    }
//                }
//                m = new ChessMove(startPos, endPos, null);
//                kList.add(m);
//            }
//        }
//
//        return kList;
//    }
//
//    public ArrayList<ChessMove> pawnMove(ChessPosition myPosition, ChessBoard board){
//        ArrayList<ChessMove> pList = new ArrayList<>();
//        ChessPosition startPos = new ChessPosition(myPosition.getRow(), myPosition.getColumn());
//        ChessMove m;
//        ChessPosition endPos;
//        outerloop:
//        if(board.getPiece(startPos).getTeamColor() == ChessGame.TeamColor.WHITE) {
//            if (startPos.getRow() + 1 <= 8) {
//                endPos = new ChessPosition(startPos.getRow() + 1, startPos.getColumn());
//                if (startPos.getColumn() - 1 > 0) {
//                    ChessPosition checkL = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() - 1);
//                    if (board.getPiece(checkL) != null) {
//                        if (board.getPiece(checkL).getTeamColor() != pieceColor) {
//                            if(endPos.getRow() == 8){
//                                m = new ChessMove(startPos, checkL, PieceType.QUEEN);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkL, PieceType.BISHOP);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkL, PieceType.KNIGHT);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkL, PieceType.ROOK);
//                                pList.add(m);
//                            }
//                            else {
//                                m = new ChessMove(startPos, checkL, null);
//                                pList.add(m);
//                            }
//                        }
//                    }
//
//                }
//                if (startPos.getColumn() + 1 <= 8) {
//                    ChessPosition checkR = new ChessPosition(startPos.getRow() + 1, startPos.getColumn() + 1);
//                    if (board.getPiece(checkR) != null) {
//                        if (board.getPiece(checkR).getTeamColor() != pieceColor) {
//                            if(checkR.getRow() == 8){
//                                m = new ChessMove(startPos, checkR, PieceType.QUEEN);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkR, PieceType.BISHOP);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkR, PieceType.KNIGHT);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkR, PieceType.ROOK);
//                                pList.add(m);
//                            }
//                            else {
//                                m = new ChessMove(startPos, checkR, null);
//                                pList.add(m);
//                                break outerloop;
//                            }
//                        }
//                    }
//                }
//                if (board.getPiece(endPos) != null) {
//                    break outerloop;
//                }
//                if(endPos.getRow() == 8){
//                    m = new ChessMove(startPos, endPos, PieceType.QUEEN);
//                    pList.add(m);
//                    m = new ChessMove(startPos, endPos, PieceType.BISHOP);
//                    pList.add(m);
//                    m = new ChessMove(startPos, endPos, PieceType.KNIGHT);
//                    pList.add(m);
//                    m = new ChessMove(startPos, endPos, PieceType.ROOK);
//                    pList.add(m);
//                }
//                else {
//                    m = new ChessMove(startPos, endPos, null);
//                    pList.add(m);
//                }
//            }
//            if (startPos.getRow() == 2) {
//                endPos = new ChessPosition(startPos.getRow() + 2, startPos.getColumn());
//                if(board.getPiece(endPos) == null) {
//                    m = new ChessMove(startPos, endPos, null);
//                    pList.add(m);
//                }
//            }
//        }
//        outerloop:
//        if(board.getPiece(startPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
//            if (startPos.getRow() - 1 > 0) {
//                endPos = new ChessPosition(startPos.getRow() - 1, startPos.getColumn());
//                if (startPos.getColumn() - 1 > 0) {
//                    ChessPosition checkL = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() - 1);
//                    if (board.getPiece(checkL) != null) {
//                        if (board.getPiece(checkL).getTeamColor() != pieceColor) {
//                            if(endPos.getRow() == 1){
//                                m = new ChessMove(startPos, checkL, PieceType.QUEEN);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkL, PieceType.BISHOP);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkL, PieceType.KNIGHT);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkL, PieceType.ROOK);
//                                pList.add(m);
//                            }
//                            else {
//                                m = new ChessMove(startPos, checkL, null);
//                                pList.add(m);
//                            }
//                        }
//                    }
//
//                }
//                if (startPos.getColumn() + 1 <= 8) {
//                    ChessPosition checkR = new ChessPosition(startPos.getRow() - 1, startPos.getColumn() + 1);
//                    if (board.getPiece(checkR) != null) {
//                        if (board.getPiece(checkR).getTeamColor() != pieceColor) {
//                            if(endPos.getRow() == 1){
//                                m = new ChessMove(startPos, checkR, PieceType.QUEEN);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkR, PieceType.BISHOP);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkR, PieceType.KNIGHT);
//                                pList.add(m);
//                                m = new ChessMove(startPos, checkR, PieceType.ROOK);
//                                pList.add(m);
//                            }
//                            else {
//                                m = new ChessMove(startPos, checkR, null);
//                                pList.add(m);
//                            }
//                        }
//                    }
//                }
//                if (board.getPiece(endPos) != null) {
//                    break outerloop;
//                }
//                if(endPos.getRow() == 1){
//                    m = new ChessMove(startPos, endPos, PieceType.QUEEN);
//                    pList.add(m);
//                    m = new ChessMove(startPos, endPos, PieceType.BISHOP);
//                    pList.add(m);
//                    m = new ChessMove(startPos, endPos, PieceType.KNIGHT);
//                    pList.add(m);
//                    m = new ChessMove(startPos, endPos, PieceType.ROOK);
//                    pList.add(m);
//                }
//                else {
//                    m = new ChessMove(startPos, endPos, null);
//                    pList.add(m);
//                }
//            }
//            if (startPos.getRow() == 7) {
//                endPos = new ChessPosition(startPos.getRow() - 2, startPos.getColumn());
//                if(board.getPiece(endPos) == null) {
//                    m = new ChessMove(startPos, endPos, null);
//                    pList.add(m);
//                }
//            }
//        }
//        return pList;
//    }


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
//        ArrayList<ChessMove> kList = kingMove(myPosition, board);
//        ListIterator<ChessMove> k = kList.listIterator();
//        ArrayList<ChessMove> nList = knightMove(myPosition, board);
//        ListIterator<ChessMove> n = nList.listIterator();
//        ArrayList<ChessMove> pList = pawnMove(myPosition, board);
//        ListIterator<ChessMove> p = pList.listIterator();

        ChessPiece piece = board.getPiece(myPosition);
        switch (piece.getPieceType()){ //depending on piece type add to list
//            case KING:
//                while(k.hasNext()){
//                    moves.add(k.next());
//                }
//                break;
//            case QUEEN:
//                while(l.hasNext()){
//                    moves.add(l.next());
//                }
//                while(r.hasNext()){
//                    moves.add(r.next());
//                }
//                while(u.hasNext()){
//                    moves.add(u.next());
//                }
//                while(d.hasNext()){
//                    moves.add(d.next());
//                }
//                while(diag.hasNext()){
//                    moves.add(diag.next());
//                }
//                break;
//            case BISHOP:
//                while(diag.hasNext()){
//                    moves.add(diag.next());
//                }
//                break;
//            case KNIGHT:
//                while(n.hasNext()){
//                    moves.add(n.next());
//                }
//                break;
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

//                break;
//            case PAWN:
//                while(p.hasNext()){
//                    moves.add(p.next());
//                }
//                break;
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
