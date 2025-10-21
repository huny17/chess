package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(int ID, String whiteUser, String blackUser, String gameName, ChessGame chessGame) {

    @Override
    public String toString() {
        String str = String.format(", whiteUsername: %s, blackUsername: %s, gameName: %s", whiteUser, blackUser, gameName);
        return "{gameID :" + ID + str + "}";
    }
}
