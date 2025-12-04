package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Session, Session> connections = new ConcurrentHashMap<>();

    public void add(Session session){
        connections.put(session, session); //ID, session?
    }

    public void remove(Session session){
        connections.remove(session);
    }

    //double check
    public void broadcast(Session excludeSession, NotificationMessage notification) throws IOException { //for sending to everyone and then root user notifications
        String msg = new Gson().toJson(notification);//what type do I want notif to be
        for (Session c : connections.values()){
            if (c.isOpen()){
                if (c.equals(excludeSession)){
                    c.getRemote().sendString(msg);
                }
            }
        }
    }

}
