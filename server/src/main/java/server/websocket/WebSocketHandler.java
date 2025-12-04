package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exceptions.GeneralException;
import io.javalin.websocket.*;
import websocket.commands.UserGameCommand;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
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
        if (checkAuth(token, id, session)) {
            connections.add(id, session);
            var message = String.format("%s connected", authDAO.getUser(token));
            connections.broadcastRoot(id, session, new LoadGameMessage(gameDAO.getGame(id).chessGame()));
            connections.broadcastOthers(id, session, new NotificationMessage(message));
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

    private ChessGame getGame(int id) throws GeneralException {
        return gameDAO.getGame(id).chessGame();
    }

    private String getUsername(String token) throws GeneralException {
        return authDAO.getUser(token);
    }

    private boolean checkAuth(String token, int id, Session session) throws IOException, GeneralException{
        if(authDAO.getUser(token) == null){
            session.getRemote().sendString(new Gson().toJson(new ErrorMessage("errormessage: Not Authorized")));
            return false;
        }
        return true;
    }
}
