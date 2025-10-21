package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(int ID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {

    @Override
    public String toString() {
        String str = String.format(", whiteUsername: %s, blackUsername: %s, gameName: %s", whiteUsername, blackUsername, gameName);
        return "{gameID :" + ID + str + "}";
    }
}
