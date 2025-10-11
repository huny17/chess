package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(int id, String whiteUser, String blackUser, String name, ChessGame game) {

    @Override
    public String toString() {
        String str = String.format("W: %s, B: %s, Name: %s", whiteUser, blackUser, name);
        return "GameData{" +
                "id=" + id + str +
                ", game=" + game.toString() +
                '}';
    }
}
