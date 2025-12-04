package websocket.messages;

import chess.ChessGame;

public class ErrorMessage extends ServerMessage{
    String message;

    public ErrorMessage(String error) {
        super(ServerMessageType.ERROR);
        this.message = error;
    }
}
