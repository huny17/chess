package server.websocket;

import chess.*;
import com.google.gson.Gson;
import dataaccess.*;
import exceptions.GeneralException;
import io.javalin.websocket.*;
import model.GameData;
import websocket.commands.*;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.*;
import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    AuthDAO authDAO;
    GameDAO gameDAO;
    MakeMoveHandler moveHelper;

    public WebSocketHandler(GameDAO gameDAO, AuthDAO authDAO){
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
        moveHelper = new MakeMoveHandler(this.gameDAO, this.authDAO);
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
                case LEAVE -> leave(command.getAuthToken(), command.getGameID(), ctx.session);
                case RESIGN -> resign(command.getAuthToken(), command.getGameID(), ctx.session);
            }
        }catch(IOException | GeneralException e){
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx){
        System.out.println("ws closed");
    }

    private void connect(String token, int id, Session session) throws IOException, GeneralException {
        if (checkInput(token, id)) {
            connections.add(id, session);
            String message = playerTeamMessage(token, id);
            connections.broadcastRoot(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
            connections.broadcastOthers(id, session, new NotificationMessage(message));
        }
        else{
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("Not Authorized")));
        }
    }

    private void makeMove(String token, int id, ChessMove move, Session session) throws IOException, GeneralException{
        GameData game = gameDAO.getGame(id);
        if(checkPlayer(token, id) && !game.chessGame().getIsGameOver()){
            if(moveHelper.allowedMove(game.chessGame(), move, moveHelper.getTeam(token, game))){
                moveHelper.updateMove(game, move);
                ChessGame newGame = game.chessGame();
                moveHelper.updateGameOver(newGame);
                gameDAO.updateGame(Integer.toString(id), new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), newGame));
                connections.broadcastRoot(id, session, new LoadGameMessage(game.chessGame()));
                connections.broadcastOthers(id, session, new LoadGameMessage(game.chessGame()));
                connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s made move %s %s", authDAO.getUser(token), game.chessGame().getBoard().getPiece(move.getEndPosition()), move)));
                if(game.chessGame().getIsGameOver()){
                    connections.broadcastOthers(id, session, new NotificationMessage(moveHelper.checkMesage(game)));
                    connections.broadcastRoot(id, session, new NotificationMessage(moveHelper.checkMesage(game)));
                }
            }
            else{
                session.getRemote().sendString(new Gson().toJson(new ErrorMessage("You cannot make this move")));
            }
        }
        else{
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("You aren't a player or this game is over")));
        }
    }

    private void leave(String token, int id, Session session) throws IOException, GeneralException{
        removeUser(token, id);
        connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s left the game", authDAO.getUser(token))));
        connections.remove(id, session);
    }

    private void resign(String token, int id, Session session) throws IOException, GeneralException{
        if(checkPlayer(token, id) & !gameDAO.getGame(id).chessGame().getIsGameOver()) {
            GameData game = gameDAO.getGame(id);
            connections.broadcastRoot(id, session, new NotificationMessage("You resigned"));
            connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s resigned the game", authDAO.getUser(token))));
            connections.remove(id, session);
            ChessGame newGame = game.chessGame();
            newGame.setGameOver();
            gameDAO.updateGame(Integer.toString(id), new GameData(game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), newGame));
        }
        else{
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("You aren't a player or this game is already over")));
        }
    }

    private boolean checkInput(String token, int id) throws GeneralException{
        if(authDAO.getUser(token) == null | gameDAO.getGame(id) == null){
            return false;
        }
        return true;
    }

    private boolean checkPlayer(String token, int id) throws GeneralException{
        if(authDAO.getAuth(token) == null){
            return false;
        }
        if(authDAO.getUser(token).equals(gameDAO.getGame(id).whiteUsername()) | authDAO.getUser(token).equals(gameDAO.getGame(id).blackUsername())){
            return true;
        }
        return false;
    }

    private void removeUser(String token, int id) throws GeneralException{
        if(authDAO.getUser(token).equals(gameDAO.getGame(id).whiteUsername())){
            gameDAO.updateWhiteTeam(Integer.toString(id), new GameData(id, null, gameDAO.getGame(id).blackUsername(), gameDAO.getGame(id).gameName(), gameDAO.getGame(id).chessGame()));
        }
        if(authDAO.getUser(token).equals(gameDAO.getGame(id).blackUsername())){
            gameDAO.updateBlackTeam(Integer.toString(id), new GameData(id, gameDAO.getGame(id).whiteUsername(), null, gameDAO.getGame(id).gameName(), gameDAO.getGame(id).chessGame()));
        }
    }

    private String playerTeamMessage(String token, int id)throws GeneralException{
        String message;
        if(authDAO.getUser(token).equals(gameDAO.getGame(id).whiteUsername()) ) {
            message = String.format("%s connected as WHITE", authDAO.getUser(token));
        }
        else if(authDAO.getUser(token).equals(gameDAO.getGame(id).blackUsername()) ) {
            message = String.format("%s connected as BLACK", authDAO.getUser(token));
        }
        else{
            message = String.format("%s connected as observer", authDAO.getUser(token));
        }
        return message;
    }
}
