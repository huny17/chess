package ui;

import java.util.*;
import chess.ChessGame;
import exceptions.*;
import model.*;
import server.ServerFacade;
import ui.interfaces.*;
import ui.theboard.BoardView;
import ui.websocket.WebSocketFacade;
import websocket.ServerMessageHandler;
import static ui.EscapeSequences.*;

public class ChessClient implements ServerMessageHandler {

    private final Prelogin prelogin;
    private final Postlogin postlogin;
    private final Gameplay gameplay;
    private HelpConsole help;

    public ChessClient(String serverUrl) throws GeneralException{
        ServerFacade server = new ServerFacade(serverUrl);
        WebSocketFacade ws = new WebSocketFacade(serverUrl, this);
        prelogin = new Prelogin(server);
        postlogin = new Postlogin(server, ws);
        gameplay = new Gameplay(server, ws);
        help = new HelpConsole();
    }

    public void run() {
        System.out.println();
        System.out.print(SET_TEXT_COLOR_WHITE+ "WELCOME TO CHESS"+"\n");
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

    public void notify(String notification){
        System.out.println(SET_TEXT_COLOR_GREEN + notification);
        printPrompt();
    }
    public void errorHandle(String notification){
        System.out.println(SET_TEXT_COLOR_RED + notification);
        printPrompt();
    }
    public void loadingGame(ChessGame game){
        BoardView.run(game, postlogin.getTeam(), null);
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
                    help.setState(State.SIGNEDIN);
                }
                case "register" ->{
                    result = prelogin.register(params);
                    help.setState(State.SIGNEDIN);
                }
                case "logout" ->{
                    help.assertSignedIn();
                    result = postlogin.logout();
                    help.setState(State.SIGNEDOUT);
                }
                case "create" -> {
                    help.assertSignedIn();
                    result = postlogin.createGame(params);
                }
                case "list" ->{
                    help.assertSignedIn();
                    result = postlogin.list();
                }
                case "play" ->{
                    help.assertSignedIn();
                    result = postlogin.play(params);
                    help.setState(State.INGAME);
                }
                case "observe" ->{
                    help.assertSignedIn();
                    result = postlogin.observe(params);
                    help.setState(State.INGAME);
                }
                case "redraw" ->{
                    help.assertInGame();
                    result = gameplay.redraw(postlogin.getGameData(), postlogin.getTeam());
                }
                case "leave" ->{
                    help.assertInGame();
                    result = gameplay.leave(postlogin.getGameData());
                    help.setState(State.SIGNEDIN);
                }
                case "move" ->{
                    help.assertInGame();
                    result = gameplay.makeMove(postlogin.getGameData(), postlogin.getTeam(), params);
                }
                case "resign" ->{
                    help.assertInGame();
                    result = gameplay.resign(postlogin.getGameData());
                }
                case "highlight" ->{
                    help.assertInGame();
                    result = gameplay.highlight(postlogin.getGameData(), postlogin.getTeam(), params);
                }
                case "quit" -> result = "quit";
                default -> result = help.helpScreen();
            }
            return result;
        }catch(GeneralException e){
            return e.getMessage();
        }
    }

}
