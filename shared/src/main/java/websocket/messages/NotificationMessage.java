package websocket.messages;

import com.google.gson.Gson;
import websocket.messages.ServerMessage.ServerMessageType;

public class NotificationMessage {
    ServerMessageType type;
    String message;

    public NotificationMessage(String message){
        this.type = ServerMessageType.NOTIFICATION;
        this.message = message;
    }
}
