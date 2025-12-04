package server.websocket;

import chess.*;
import com.google.gson.Gson;
import dataaccess.*;
import exceptions.GeneralException;
import io.javalin.websocket.*;
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
        if (checkInput(token, id)) {
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
        if(checkPlayer(token, id)) {
            if (moveHelper.allowedMove(gameDAO.getGame(id).chessGame(), move.getEndPosition(), move, moveHelper.getTeam(token, gameDAO.getGame(id)))) {
                moveHelper.updateMove(gameDAO.getGame(id), move, moveHelper.getTeam(token, gameDAO.getGame(id)));
                connections.broadcastRoot(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
                connections.broadcastOthers(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
                connections.broadcastOthers(id, session, new NotificationMessage(String.format("%s made move %s", authDAO.getUser(token), move)));
                connections.remove(id, session);
            }
        }
        else{
            ErrorMessage err = new ErrorMessage("Not Authorized");
            String huh = new Gson().toJson(err);
            session.getRemote().sendString(huh);
        }
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

    private boolean checkInput(String token, int id) throws GeneralException{
        if(authDAO.getUser(token) == null | gameDAO.getGame(id) == null){
            return false;
        }
        return true;
    }

    private boolean checkPlayer(String token, int id) throws GeneralException{
        if(authDAO.getUser(token).equals(gameDAO.getGame(id).whiteUsername()) | authDAO.getUser(token).equals(gameDAO.getGame(id).whiteUsername())){
            return true;
        }
        return false;
    }

}
