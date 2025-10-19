package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(int id, String whiteUser, String blackUser, String gameName, ChessGame chessGame) {

    @Override
    public String toString() {
        String str = String.format("W: %s, B: %s, Name: %s", whiteUser, blackUser, gameName);
        return "GameData{" +
                "id=" + id + str +
                ", game=" + chessGame.toString() +
                '}';
    }
}
