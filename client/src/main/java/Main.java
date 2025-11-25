import chess.*;
import exceptions.GeneralException;
import ui.*;

public class Main {
    public static void main(String[] args) throws  GeneralException {
        try {
            ChessClient client = new ChessClient("http://localhost:8080");
            client.run();
        }catch(GeneralException e){
            throw new GeneralException(GeneralException.ExceptionType.invalid, e.getMessage());
        }

        /** testing the board **/
//        ChessGame g = new ChessGame();
//        BlackBoardView.run(g.getBoard());
//        WhiteBoardView.run(g.getBoard());

    }
}