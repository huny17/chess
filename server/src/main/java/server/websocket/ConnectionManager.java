package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Collection<Session>> connections = new ConcurrentHashMap<>();

    public void add(int id, Session session){
        if(connections.get(id) != null) {
            Collection<Session> userConnections = connections.get(id);
            userConnections.add(session);
            connections.put(id, userConnections);
        }
        else{
            Collection<Session> userConnected = new ArrayList<>();
            userConnected.add(session);
            connections.put(id, userConnected);
        }
    }

    public void remove(int id, Session session){
        if(connections.get(id) != null) {
            Collection<Session> userConnections = connections.get(id);
            userConnections.remove(session);
            connections.put(id, userConnections);
        }
    }

    public void broadcastRoot(int id, Session excludeSession, ServerMessage notification) throws IOException { //for sending to everyone and then root user notifications
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

    public void broadcastOthers(int id, Session excludeSession, ServerMessage notification) throws IOException { //for sending to everyone and then root user notifications
        String msg = new Gson().toJson(notification);
        Collection<Session> userConnections = connections.get(id);
        for (Session c : userConnections){
            if (c.isOpen()){
                if (!c.equals(excludeSession)){
                    c.getRemote().sendString(msg);
                }
            }
        }
    }
}
