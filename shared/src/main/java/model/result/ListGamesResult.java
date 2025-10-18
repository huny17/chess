package model.result;

import model.GameData;

import java.util.Collection;

public record ListGamesResult(String games, Collection<GameData> gameInfo){
}
