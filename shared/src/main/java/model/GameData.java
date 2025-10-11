package model;

import chess.ChessGame;

import java.util.Objects;

public record GameData(int id, String whiteUser, String blackUser, String name, ChessGame game) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return id == gameData.id && Objects.equals(whiteUser, gameData.whiteUser) && Objects.equals(blackUser, gameData.blackUser) && Objects.equals(name, gameData.name) && Objects.equals(game, gameData.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, whiteUser, blackUser, name, game);
    }

    @Override
    public String toString() {

        String str = String.format("W: %s, B: %s, Name: %s", whiteUser, blackUser, name);

        return "GameData{" +
                "id=" + id + str +
                ", game=" + game.toString() +
                '}';
    }
}
