package websocket.commands;

import chess.*;

public class MakeMoveCommand extends UserGameCommand{

    ChessMove move;

    public MakeMoveCommand(String authToken, int gameID,ChessMove move){
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.move = move;
    }

    public ChessMove getChessMove() {
        return move;
    }
}
