package ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import com.google.gson.Gson;
import model.*;
import server.ServerFacade;
import javax.management.Notification;
import static ui.EscapeSequences.*;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) throws Exception {
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

    public void notify(Notification notification){
        System.out.println();
        printPrompt();
    }

    private void printPrompt() {
        System.out.println("\n" + SET_TEXT_COLOR_BLACK + ">>> " + SET_TEXT_COLOR_GREEN);
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
                case "create game" -> createGame(params);
                case "list games" -> listGames();
                case "play game" -> join();
                case "observe games" -> join(); //observe function?
                case "quit" -> "quit";
                default -> help();
            };
        }catch(Exception e){
            return e.getMessage();
        }
    }

    public String login(String... params) throws Exception{
        if(params.length == 2){
            state = State.SIGNEDIN;
            AuthData loggedIn = server.loginUser(params[0], params[1]));
            visitorName = String.join("-", params[0]);
            return String.format("LOGGED_IN %s", visitorName);
        }
        throw new Exception();
    }

    public String register(String... params) throws Exception{
        if(params.length == 3){
            UserData registered = server.addUser(new UserData(params[0], params[1], params[2]));
            state = State.SIGNEDIN;
            visitorName = String.join("-", registered.username());
            return String.format("LOGGED_IN %s", visitorName);
        }
        throw new Exception();
    }

    public String logout() throws Exception{
        assertSignedIn();
        state = State.SIGNEDOUT;
        return "LOGGED_OUT";
    }

    public String createGame(String... params) throws Exception{
        assertSignedIn();
        if(params.length == 1) {
            GameData game = server.createGame(params);
            return String.format("Game %s created", game.gameName());
        }
        throw new Exception();
    }

    public String listGames() throws Exception{
        assertSignedIn();
        Collection<GameData> games = server.listGames();
        var result = new StringBuilder();
        var gson = new Gson();
        for (GameData game : games) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }

    public String join(String... params) throws Exception{
        assertSignedIn();
        if(params.length == 2) {
            try{
                int id = Integer.parseInt(params[0]);
                GameData game = getGame(id);
                if(game != null){
                    GameData game = server.joinGame(params);
                    return; //give board?
                }
            }catch(NumberFormatException ignored){
            }
            throw new Exception();
        }
        throw new Exception();
    }


    private GameData getGame(int id) throws Exception{
        for(GameData game : server.listGames()){
            if(game.gameID() == id){
                return game;
            }
        }
        return null;
    }

    public String help(){
        if(state == State.SIGNEDOUT){
            return """
                    register <username> <password> <email> - to create an account
                    login <username> <password> - to play chess
                    quit - playing chess
                    help - with possible commands
                    """;
        }
        return """
                create <name> - a game
                list - games
                join <ID> [WHITE|BLACK] - a game
                observe <ID> - a game
                logout - when you are done
                quit - playing chess
                help - with possible commands
                """;
    }

    private void assertSignedIn() throws Exception{
        if(state == State.SIGNEDOUT){
            throw new Exception();
        }
    }
}
