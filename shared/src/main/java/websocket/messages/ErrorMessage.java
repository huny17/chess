package websocket.messages;

import chess.ChessGame;

public class ErrorMessage extends ServerMessage{
    String errormessage;

    public ErrorMessage(String message) {
        super(ServerMessageType.ERROR);
        this.errormessage = message;
    }
}
