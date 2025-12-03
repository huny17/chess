package server.websocket;

import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsMessageHandler;
import io.javalin.websocket.WsConnectHandler;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {
}
