package server.websocket;

import chess.*;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exceptions.GeneralException;
import model.GameData;
import java.util.Collection;

public class MakeMoveHandler {

    AuthDAO authDAO;
    GameDAO gameDAO;

    public MakeMoveHandler(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public void updateMove(GameData data, ChessMove move) throws GeneralException {
        try {
            ChessGame game = data.chessGame();
            game.makeMove(move);
            gameDAO.updateGame(data.gameID().toString(), new GameData(data.gameID(), data.whiteUsername(), data.blackUsername(), data.gameName(), game));
        } catch (InvalidMoveException e) {
            throw new GeneralException(GeneralException.ExceptionType.invalid, e.getMessage(), e);
        }
    }

    public ChessGame.TeamColor getTeam(String token, GameData gameInfo) throws GeneralException{
        if (authDAO.getUser(token).equals(gameInfo.whiteUsername())){
            return ChessGame.TeamColor.WHITE;
        }
        if (authDAO.getUser(token).equals(gameInfo.blackUsername())){
            return ChessGame.TeamColor.BLACK;
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "user not a player in game");
    }

    public boolean checkTeam(ChessGame game, ChessPosition pos, ChessGame.TeamColor color) throws GeneralException{
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(piece == null){
            return false;
        }
        if(!piece.getTeamColor().equals(color)){
            return false;
        }
        if(!piece.getTeamColor().equals(game.getTeamTurn())){
            return false;
        }
        return true;
    }

    public boolean checkMoveValidity(ChessGame game, ChessPosition pos, ChessMove move){
        ChessPiece piece = game.getBoard().getPiece(pos);
        if(piece == null){
            return false;
        }
        Collection<ChessMove> validMoves = piece.pieceMoves(game.getBoard(), pos);
        if(validMoves.contains(move)){
            return true;
        }
        return false;
    }

    public boolean allowedMove(ChessGame game, ChessMove move, ChessGame.TeamColor color) throws GeneralException{
        if(checkTeam(game, move.getStartPosition(), color)){
            if(checkMoveValidity(game, move.getStartPosition(), move)) {
                return true;
            }
        }
        return false;
    }

    public void updateGameOver(ChessGame game){
        if(game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.WHITE)){
            game.setGameOver();
        }
        if(game.isInCheckmate(ChessGame.TeamColor.BLACK) || game.isInStalemate(ChessGame.TeamColor.BLACK)){
            game.setGameOver();
        }
    }

    public String checkMesage(GameData gameInfo){
        if(getTeamState(gameInfo, ChessGame.TeamColor.BLACK) != null){
            return getTeamState(gameInfo, ChessGame.TeamColor.BLACK);
        }
        else if(getTeamState(gameInfo, ChessGame.TeamColor.WHITE) != null){
            return getTeamState(gameInfo, ChessGame.TeamColor.WHITE);
        }
        return null;
    }

    public String getTeamState(GameData gameInfo, ChessGame.TeamColor color){
        String user = getPlayer(gameInfo, color);
        ChessGame game = gameInfo.chessGame();
        if(game.isInCheckmate(color)){
            return String.format("%s is in Checkmate", user);
        }
        else if(game.isInStalemate(color)){
            return "Stalemate";
        }
        else if(game.isInCheck(color)){
            return String.format("%s is in Check", user);
        }
        return null;
    }

    public String getPlayer(GameData gameInfo, ChessGame.TeamColor color){
        if(color == ChessGame.TeamColor.WHITE){
            return gameInfo.whiteUsername();
        }
        if(color == ChessGame.TeamColor.BLACK){
            return gameInfo.blackUsername();
        }
        return null;
    }

}
