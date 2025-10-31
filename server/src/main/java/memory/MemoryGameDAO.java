package memory;

import chess.ChessGame;
import dataaccess.GameDAO;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

    private final HashMap<Integer, GameData> games = new HashMap<>();
    private Integer index = 1;

    @Override
    public void clear(){
        games.clear();
    }

    @Override
    public Integer createGame(String gameName) {
        GameData game = new GameData(index,null,null, gameName, new ChessGame());
        index += 1;
        games.put(game.gameID(), game);
        return game.gameID();
    }

    @Override
    public String getBlackUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.blackUsername();
    }

    @Override
    public String getWhiteUser(String gameId) {
        GameData game = games.get(Integer.parseInt(gameId));
        return game.whiteUsername();
    }

    @Override
    public GameData getGame(int gameId) {
        return games.get(gameId);
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void updateGame(String gameId, GameData newGame) {
        games.put(Integer.parseInt(gameId), newGame);
    }

    @Override
    public void updateWhiteTeam(String gameId, GameData newGame) {
        games.put(Integer.parseInt(gameId), newGame);
    }

    @Override
    public void updateBlackTeam(String gameId, GameData newGame) {
        games.put(Integer.parseInt(gameId), newGame);
    }
}
