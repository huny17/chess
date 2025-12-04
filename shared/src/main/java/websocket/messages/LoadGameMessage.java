package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage {
    ServerMessage.ServerMessageType type;
    ChessGame game;

    public LoadGameMessage(ChessGame game){
        this.type = ServerMessage.ServerMessageType.LOAD_GAME;
        this.game = game;
    }
}
