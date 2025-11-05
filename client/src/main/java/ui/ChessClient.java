package ui;

import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import model.*;

import javax.management.Notification;
import static ui.EscapeSequences.*;


public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) throws Exception {
        server = new ServerFacade();

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
            String[] tokens = input.toLowerCase.split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd){
                case "login" -> login();
                case "register" -> register();
                case "logout" -> logout();
                case "create game" -> createGame();
                case "list games" -> listGames();
                case "play game" -> playGame();
                case "observe games" -> observeGame();
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
            visitorName = String.join("-", params[0]);
            return String.format("LOGGED_IN %s", visitorName);
        }
        throw new Exception();
    }

    public String register(String... params) throws Exception{
        if(params.length == 3){
            state = State.SIGNEDIN;
            visitorName = String.join("-", params[0]);
            return String.format("LOGGED_IN %s", visitorName);
        }
        throw new Exception();
    }

    public String logout(String... params) throws Exception{
        assertSignedIn();
        return "LOGGED_OUT";
    }

    public String createGame(String name) throws Exception{
        assertSignedIn();
        //create game
        return String.format("Game %s created", name);
    }

    public String listGame() throws Exception{
        assertSignedIn();
        //list game
        return //listed games
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
