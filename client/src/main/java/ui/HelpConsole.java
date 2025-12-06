package ui;

import exceptions.GeneralException;

import static ui.EscapeSequences.*;

public class HelpConsole {

    private State state = State.SIGNEDOUT;

    public HelpConsole(){
    }

    public String helpScreen(){
        if(state == State.INGAME){
            return  SET_TEXT_COLOR_BLUE + """
                    move <start position(col, row)> <end position(col, row)> - a piece
                    redraw - the chessboard
                    leave - exiting the current game
                    resign - admitting defeat
                    highlight <start position(row, col)> - possible moves
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
        return SET_TEXT_COLOR_BLUE+
                """
                register <username> <password> <email> - to create an account
                login <username> <password> - to play chess
                quit - playing chess
                help - with possible commands
                """;

        }

    public void setState(State state){
        this.state = state;
    }

    public void assertSignedIn() throws GeneralException {
        if(state == State.SIGNEDOUT){
            throw new GeneralException(GeneralException.ExceptionType.unauthorized, "Please login to use these commands");
        }
    }

    public void assertInGame() throws GeneralException{
        if(state != State.INGAME){
            throw new GeneralException(GeneralException.ExceptionType.unauthorized, "Please play or observe a game to use these commands");
        }
    }
}
