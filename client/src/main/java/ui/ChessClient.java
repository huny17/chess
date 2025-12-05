package ui;

import java.util.*;
import exceptions.*;
import model.*;
import server.ServerFacade;
import ui.interfaces.*;
import ui.websocket.WebSocketFacade;
import websocket.messages.ServerMessage;
import static ui.EscapeSequences.*;

public class ChessClient {
    private State state = State.SIGNEDOUT;
    private final Prelogin prelogin;
    private final Postlogin postlogin;
    private final Gameplay gameplay;
    private HelpConsole help;
    private GameData chessGame;
    private String color = null;

    public ChessClient(String serverUrl) throws GeneralException{
        ServerFacade server = new ServerFacade(serverUrl);
        WebSocketFacade ws = new WebSocketFacade(serverUrl, this::notify);
        prelogin = new Prelogin(server);
        postlogin = new Postlogin(server, ws);
        gameplay = new Gameplay(server, ws);
        help = new HelpConsole(state);
    }

    public void run() {
        System.out.println();
        System.out.print(help.helpScreen());
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

    public void notify(ServerMessage notification){
        System.out.println(SET_TEXT_COLOR_GREEN + notification);
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
                    help = new HelpConsole(state);
                }
                case "register" ->{
                    result = prelogin.register(params);
                    state = State.SIGNEDIN;
                    help = new HelpConsole(state);
                }
                case "logout" ->{
                    assertSignedIn();
                    result = postlogin.logout();
                    state = State.SIGNEDOUT;
                    help = new HelpConsole(state);
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
                    chessGame = postlogin.getGameData(params);
                    color = params[0];
                    state = State.INGAME;
                    help = new HelpConsole(state);
                }
                case "observe" ->{
                    assertSignedIn();
                    result = postlogin.observe(params);
                    color = "white";
                    state = State.INGAME;
                    help = new HelpConsole(state);
                }
                case "redraw" ->{
                    assertInGame();
                    result = gameplay.redraw(chessGame, color);
                }
                case "leave" ->{
                    assertInGame();
                    result = gameplay.leave(chessGame);
                    color = null;
                    state = State.SIGNEDIN;
                    help = new HelpConsole(state);
                }
                case "move" ->{
                    assertInGame();
                    result = gameplay.makeMove(chessGame, color, params);
                }
                case "resign" ->{
                    assertInGame();
                    result = gameplay.resign(chessGame);
                    color = null;
                    state = State.SIGNEDIN;
                    help = new HelpConsole(state);
                }
                case "highlight" ->{
                    assertInGame();
                    result = gameplay.highlight(chessGame, color, params);
                }
                case "quit" -> result = "quit";
                default -> result = help.helpScreen();
            }
            return result;
        }catch(GeneralException e){
            return e.getMessage();
        }
    }

    private void assertSignedIn() throws GeneralException{
        if(state == State.SIGNEDOUT){
            throw new GeneralException(GeneralException.ExceptionType.unauthorized, "Please login to use these commands");
        }
    }

    private void assertInGame() throws GeneralException{
        if(state != State.INGAME){
            throw new GeneralException(GeneralException.ExceptionType.unauthorized, "Please play or observe a game to use these commands");
        }
    }
}
