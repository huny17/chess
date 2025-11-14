package ui;

import java.util.*;

import Exceptions.*;
import model.*;
import model.request.CreateGameRequest;
import model.request.JoinGameRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.ListGamesResult;
import server.ServerFacade;
import static ui.EscapeSequences.*;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;
    private TreeMap<Integer, GameData> listedGames = new TreeMap<>();

    public ChessClient(String serverUrl){
        server = new ServerFacade(serverUrl);
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
        state = State.SIGNEDOUT;
        String loggedOut = server.logout();
        return "LOGGED_OUT";
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
        var result = new StringBuilder();
        for (GameData game : games.games()) {
            result.append("ID: ").append(i).append(game.simpleString()).append('\n');;
            listedGames.put(i, game);
            i++;
        }
        return result.toString();
    }

    public String play(String... params) throws GeneralException{
        assertSignedIn();
        if(params.length == 2) {
            try{
                int id = Integer.parseInt(params[0]);
                GameData findGame = getGame(id);
                if(findGame != null){
                    GameData connectGame = server.joinGame(new JoinGameRequest(params[0], params[1]));
                    String notification = String.format("You are now playing %s", connectGame.gameName());
                    System.out.print(notification);
                    //check color
                    if(params[1].equals("white")){
                        WhiteBoardView.run(connectGame.chessGame().getBoard());
                    }
                    if(params[1].equals("black")){
                        BlackBoardView.run(connectGame.chessGame().getBoard());
                    }
                    return notification;
                }
            }catch(NumberFormatException ignored){
            }
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <ID> [WHITE|BLACK]");
    }

    public String observe(String... params) throws GeneralException{
        assertSignedIn();
        if(params.length == 2) {
            try{
                int id = Integer.parseInt(params[0]);
                GameData findGame = getGame(id);
                if(findGame != null){
                    String notification = String.format("You are now observing %s", findGame.gameName());
                    WhiteBoardView.run(findGame.chessGame().getBoard());
                    return notification;
                }
            }catch(NumberFormatException ignored){
            }
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <ID>");
    }

    private GameData getGame(int id) throws GeneralException{
        return listedGames.get(id);

        //return null;
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
                play <ID> [WHITE|BLACK] - a game
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
