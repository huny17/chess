package ui;

import java.util.*;
import exceptions.*;
import model.*;
import server.ServerFacade;
import ui.websocket.WebSocketFacade;
import websocket.Notification;
import static ui.EscapeSequences.*;

public class ChessClient {
    private State state = State.SIGNEDOUT;
    private final Prelogin prelogin;
    private final Postlogin postlogin;
    private final Gameplay gameplay;

    public ChessClient(String serverUrl) throws GeneralException{
        ServerFacade server = new ServerFacade(serverUrl);
        WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
        prelogin = new Prelogin(server);
        postlogin = new Postlogin(server, ws);
        gameplay = new Gameplay(server, ws);
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
        System.out.println( notification.message());
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + ">>> " + SET_TEXT_COLOR_BLUE);
    }

    public String eval(String input){
        try{
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            String result;
            switch (cmd){
                case "login"->{
                    result = prelogin.login(params);
                    state = State.SIGNEDIN;
                }
                case "register" ->{
                    result = prelogin.register(params);
                    state = State.SIGNEDIN;
                }
                case "logout" ->{
                    assertSignedIn();
                    result = postlogin.logout();
                    state = State.SIGNEDOUT;
                }
                case "create" -> {
                    assertSignedIn();
                    result = postlogin.createGame(params);
                }
                case "list" ->{
                    assertSignedIn();
                    result = postlogin.list();
                }
                case "play" ->{
                    assertSignedIn();
                    result = postlogin.play(params);
                }
                case "observe" ->{
                    assertSignedIn();
                    result = postlogin.observe(params);
                }
                case "redraw" ->{
                }
                case "leave" ->{
                }
                case "make move" ->{
                }
                case "resign" ->{
                }
                case "legal moves" ->{
                }
                case "quit" -> result = "quit";
                default -> result = help();
            }
            return result;
        }catch(GeneralException e){
            return e.getMessage();
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
