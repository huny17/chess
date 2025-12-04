package server.websocket;

import chess.*;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exceptions.GeneralException;
import io.javalin.websocket.*;
import model.GameData;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    AuthDAO authDAO;
    GameDAO gameDAO;

    public WebSocketHandler(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    @Override
    public void handleConnect(WsConnectContext ctx){
        System.out.println("ws connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx){
        try{
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (command.getCommandType()){
                case CONNECT -> connect(command.getAuthToken(), command.getGameID(), ctx.session);//method call
                case MAKE_MOVE -> { MakeMoveCommand moveCommand = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                    makeMove(moveCommand.getAuthToken(), moveCommand.getGameID(), moveCommand.getChessMove(), ctx.session);
                }
            }
        }catch(IOException | GeneralException e){
            e.getMessage();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx){
        System.out.println("ws closed");
    }

    private void connect(String token, int id, Session session) throws IOException, GeneralException {
        if (checkInput(token, id, session)) {
            connections.add(id, session);
            var message = String.format("%s connected", authDAO.getUser(token));
            connections.broadcastRoot(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
            connections.broadcastOthers(id, session, new NotificationMessage(message));
        }
        else{
            ErrorMessage err = new ErrorMessage("Not Authorized");
            String huh = new Gson().toJson(err);
            session.getRemote().sendString(huh);
        }
    }

    private void makeMove(String token, int id, ChessMove move, Session session) throws IOException, GeneralException{
        updateMove(gameDAO.getGame(id), move, getTeam(token, gameDAO.getGame(id)));
        connections.broadcastRoot(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
        connections.broadcastOthers(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
        connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s made move %s", authDAO.getUser(token), move)));
        connections.remove(id, session);
    }


    private void leave(String token, int id, Session session) throws IOException, GeneralException{
        connections.broadcastRoot(id, session, new NotificationMessage("You left the game"));
        connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s left the game", authDAO.getUser(token))));
        connections.remove(id, session);
    }

    private void resign(String token, int id, Session session) throws IOException, GeneralException{
        connections.broadcastRoot(id, session, new NotificationMessage("You resigned"));
        connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s resigned the game", authDAO.getUser(token))));
        connections.remove(id, session);
    }

    private boolean checkInput(String token, int id, Session session) throws IOException, GeneralException{
        if(authDAO.getUser(token) == null | gameDAO.getGame(id) == null){
            return false;
        }
        return true;
    }

    public void updateMove(GameData data, ChessMove move, ChessGame.TeamColor color) throws GeneralException{
        checkTeam(data.chessGame(), move.getStartPosition(), color);
        try {
            ChessGame game = data.chessGame();
            game.makeMove(move);
            gameDAO.updateGame(data.gameID().toString(), new GameData(data.gameID(), data.whiteUsername(), data.blackUsername(), data.gameName(), game));
        } catch (InvalidMoveException e) {
            throw new GeneralException(GeneralException.ExceptionType.invalid, e.getMessage());
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
        if(!piece.getTeamColor().equals(color)){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "You can only move pieces from your own team");
        }
        if(!piece.getTeamColor().equals(game.getTeamTurn())){
            throw new GeneralException(GeneralException.ExceptionType.invalid, "Waiting for opponent to make a move");
        }
        return true;
    }
}
