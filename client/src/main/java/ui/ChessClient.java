package ui;

public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) throws Exception {
        server = new ServerFacade();

    }

    public void run() {
        System.outprint
    }
}
