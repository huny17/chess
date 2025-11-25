package ui;

import static ui.EscapeSequences.*;

public class HelpConsole {

    private State state;

    public HelpConsole(State state){
        this.state = state;
    }

    public String helpScreen(){
        if(state == State.INGAME){
            return  SET_TEXT_COLOR_BLUE + """
                    move <start position> <end position> - a piece
                    redraw - the chessboard
                    leave - exiting the current game
                    resign - admitting defeat
                    highlight <start position> - possible moves
                    quit - playing chess
                    help - with possible commands
                    """;
        }
        if(state == State.SIGNEDIN) {
            return SET_TEXT_COLOR_BLUE + """
                    create <name> - a game
                    list - games
                    play [WHITE|BLACK] <ID> - a game
                    observe <ID> - a game
                    logout - when you are done
                    quit - playing chess
                    help - with possible commands
                    """;
        }
        return SET_TEXT_COLOR_WHITE+
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
}
