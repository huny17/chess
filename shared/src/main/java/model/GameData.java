package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {

    @Override
    public String toString() {
        String str = String.format(", whiteUsername: %s, blackUsername: %s, gameName: %s", whiteUsername, blackUsername, gameName);
        return "{gameID: " + gameID + str + "}";
    }

    public String simpleString() {
        return String.format(", whiteUsername: %s, blackUsername: %s, gameName: %s", whiteUsername, blackUsername, gameName);
    }
}
