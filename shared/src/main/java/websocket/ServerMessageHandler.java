package websocket;

import chess.ChessGame;
import websocket.messages.ServerMessage;

public interface ServerMessageHandler {
    void notify(String notification);
    void errorHandle(String err);
    void loadingGame(ChessGame game);
}
