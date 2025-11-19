package ui;

import java.util.*;

import exceptions.*;
import model.*;
import model.request.CreateGameRequest;
import model.request.JoinGameRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.ListGamesResult;
import server.ServerFacade;
import websocket.WebSocketFacade;

import static ui.EscapeSequences.*;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    //private final WebSocketFacade ws;
    private State state = State.SIGNEDOUT;
    private final TreeMap<Integer, GameData> listedGames = new TreeMap<>();

    public ChessClient(String serverUrl){
        server = new ServerFacade(serverUrl);
        //ws = new WebSocketFacade(serverUrl, this);
    }

    public void run() {
        System.out.println();
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();
            try{
                result = eval(line);
                System.out.print(result);
            }catch(Throwable e){
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

//    public void notify(Notification notification){
//        System.out.println( notification.message());
//        printPrompt();
//    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_BLUE);
    }

    public String eval(String input){
        try{
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd){
                case "login" -> login(params);
                case "register" -> register(params);
                case "logout" -> logout();
                case "create" -> createGame(params);
                case "list" -> list();
                case "play" -> play(params);
                case "observe" -> observe(params);
                case "quit" -> "quit";
                default -> help();
            };
        }catch(GeneralException e){
            return e.getMessage();
        }
    }

    public String login(String... params) throws GeneralException{
        if(params.length == 2){
            state = State.SIGNEDIN;
            String loggedIn = server.login(new LoginRequest(params[0], params[1]));
            visitorName = String.join("-", loggedIn);
            //ws.
            return String.format("LOGGED_IN AS %s", visitorName);
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }

    public String register(String... params) throws GeneralException{
        if(params.length == 3){
            String registered = server.register(new RegisterRequest(params[0], params[1], params[2]));
            state = State.SIGNEDIN;
            visitorName = String.join("-", registered);
            return String.format("LOGGED_IN AS %s", visitorName);
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password> <email>");
    }

    public String logout() throws GeneralException{
        assertSignedIn();
        //ws.
        state = State.SIGNEDOUT;
        String loggedOut = server.logout();
        return String.format("%s LOGGED_OUT", visitorName);
    }

    public String createGame(String... params) throws GeneralException{
        assertSignedIn();
        if(params.length == 1) {
            GameData game = server.createGame(new CreateGameRequest(params[0]));
            return String.format("Game %s created", params[0]);
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <name> ");
    }

    public String list() throws GeneralException{
        int i = 1;
        assertSignedIn();
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
        assertSignedIn();
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
                    //ws.
                    return SET_TEXT_COLOR_BLUE+notification;
                }
            }catch(NumberFormatException ignored){
            }
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: [WHITE|BLACK] <ID>");
    }

    public String observe(String... params) throws GeneralException{
        assertSignedIn();
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

    public String help(){
        if(state == State.SIGNEDOUT){
            return
                    SET_TEXT_COLOR_WHITE+
                    """
                    WELCOME TO CHESS
                    """ +SET_TEXT_COLOR_BLUE+
                    """
                    register <username> <password> <email> - to create an account
                    login <username> <password> - to play chess
                    quit - playing chess
                    help - with possible commands
                    """;
        }
        return SET_TEXT_COLOR_BLUE+"""
                create <name> - a game
                list - games
                play [WHITE|BLACK] <ID> - a game
                observe <ID> - a game
                logout - when you are done
                quit - playing chess
                help - with possible commands
                """;
    }

    private void assertSignedIn() throws GeneralException{
        if(state == State.SIGNEDOUT){
            throw new GeneralException(GeneralException.ExceptionType.unauthorized, "Please login to use these commands");
        }
    }
}
