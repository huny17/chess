import chess.*;
import ui.*;

public class Main {
    public static void main(String[] args) {

        ChessClient client = new ChessClient("http://localhost:8080");

        client.run();

        /** testing the board **/
//        ChessGame g = new ChessGame();
//
//        BlackBoardView.run(g.getBoard());
//        WhiteBoardView.run(g.getBoard());

    }
}