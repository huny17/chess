import chess.*;
import exceptions.GeneralException;
import ui.*;
import ui.theboard.BoardView;
import ui.theboard.Highlight;

public class Main {
    public static void main(String[] args) throws  GeneralException {
//        try {
//            ChessClient client = new ChessClient("http://localhost:8080");
//            client.run();
//        }catch(GeneralException e){
//            throw new GeneralException(GeneralException.ExceptionType.invalid, e.getMessage());
//        }

        /** testing the board **/
        ChessGame g = new ChessGame();
        BoardView.run(g.getBoard(),"white", null);
        BoardView.run(g.getBoard(),"black", null);
//        Highlight.run(new ChessPosition(2,1));
//

    }
}