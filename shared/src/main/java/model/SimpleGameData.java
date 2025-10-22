package model;

import chess.ChessGame;

public record SimpleGameData(int ID, String whiteUsername, String blackUsername, String gameName) {

    @Override
    public String toString() {
        String str = String.format(", whiteUsername: %s, blackUsername: %s, gameName: %s", whiteUsername, blackUsername, gameName);
        return "{gameID: " + ID + str + "}";
    }
}
