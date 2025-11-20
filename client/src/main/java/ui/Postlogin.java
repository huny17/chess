package ui;

import exceptions.GeneralException;
import model.GameData;
import model.request.CreateGameRequest;
import model.request.JoinGameRequest;
import model.result.ListGamesResult;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;

import java.util.TreeMap;

import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Postlogin {
    private String visitorName = null;
    private final TreeMap<Integer, GameData> listedGames = new TreeMap<>();
    private final ServerFacade server;
    private final WebSocketFacade ws;

    public Postlogin(ServerFacade server, WebSocketFacade ws) throws GeneralException{
        this.server = server;
        this.ws = ws;
        visitorName  = server.getName();
    }

    public String logout() throws GeneralException {
        server.logout();
        return String.format("%s LOGGED_OUT", visitorName);
    }

    public String createGame(String... params) throws GeneralException{
        if(params.length == 1) {
            server.createGame(new CreateGameRequest(params[0]));
            return String.format("Game %s created", params[0]);
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <name> ");
    }

    public String list() throws GeneralException{
        int i = 1;
        ListGamesResult games = server.listGames();
        if(games.games().isEmpty()) {
            return "No games have been created yet. Create a Game!";
        }
        var result = new StringBuilder();
        for (GameData game : games.games()) {
            result.append("ID: ").append(i).append(game.simpleString()).append('\n');
            listedGames.put(i, game);
            i++;
        }
        return result.toString();
    }

    public String play(String... params) throws GeneralException{
        populateList();
        if(params.length == 2 && checkTeam(params[0])) {
            try{
                int id = Integer.parseInt(params[1]);
                if(!listedGames.containsKey(id)){
                    throw new GeneralException(GeneralException.ExceptionType.invalid, "That game does not exist yet, try another game.");
                }
                GameData findGame = listedGames.get(id);
                if(findGame != null){
                    server.joinGame(new JoinGameRequest(params[0].toUpperCase(), findGame.gameID().toString()));
                    String notification = String.format("You are now playing %s", findGame.gameName());
                    runGame(params[0], findGame);
                    ws.makeConnection(server.getToken(), findGame.gameID());
                    return SET_TEXT_COLOR_BLUE+notification;
                }
            }catch(NumberFormatException ignored){
            }
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: [WHITE|BLACK] <ID>");
    }

    public String observe(String... params) throws GeneralException{
        populateList();
        if(params.length == 1) {
            try{
                int id = Integer.parseInt(params[0]);
                GameData findGame = listedGames.get(id);
                if(findGame != null){
                    String notification = String.format(SET_TEXT_COLOR_BLUE+"You are now observing %s", findGame.gameName());
                    WhiteBoardView.run(findGame.chessGame().getBoard());
                    return notification;
                }
            }catch(NumberFormatException ignored){
            }
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <ID>");
    }

    private void runGame(String color, GameData game) throws GeneralException{
        if(color.equals("white")){
            WhiteBoardView.run(game.chessGame().getBoard());
        }
        if(color.equals("black")){
            BlackBoardView.run(game.chessGame().getBoard());
        }
    }

    private boolean checkTeam(String color) throws GeneralException{
        if(color.equals("white") | color.equals("black")){
            return true;
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: [WHITE|BLACK] <ID>");
    }

    private void populateList() throws GeneralException{
        int i = 1;
        ListGamesResult games = server.listGames();
        for (GameData game : games.games()) {
            listedGames.put(i, game);
            i++;
        }
    }


}
