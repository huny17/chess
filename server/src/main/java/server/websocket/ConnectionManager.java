package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import server.Server;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Collection<Session>> connections = new ConcurrentHashMap<>();

    public void add(int id, Session session){
        Collection <Session> userConnections = addGameSessions(id, session);
        connections.put(id, userConnections); //ID, session?
    }

    public Collection<Session> addGameSessions(int id, Session session){
        Collection<Session> userConnections = connections.get(id);
        return userConnections.add(session);
    }

    public void remove(int id, Session session){
        removeGameSessions(id, session);
        connections.remove(session);
    }

    public Collection<Session> removeGameSessions(int id, Session session){
        Collection<Session> userConnections = connections.get(id);
        return userConnections.remove(session);
    }

    //double check
    public void broadcast(int id, Session excludeSession, ServerMessage notification) throws IOException { //for sending to everyone and then root user notifications
        String msg = new Gson().toJson(notification);
        Collection<Session> userConnections = connections.get(id);
        for (Session c : userConnections){
            if (c.isOpen()){
                if (c.equals(excludeSession)){
                    c.getRemote().sendString(msg);
                }
            }
        }
    }

}
