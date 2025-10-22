package model.result;

import model.GameData;
import model.SimpleGameData;

import java.util.Collection;

public record ListGamesResult(Collection<GameData> games){
}
