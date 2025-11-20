package ui;

import exceptions.GeneralException;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import server.ServerFacade;
import static ui.EscapeSequences.*;

public class Prelogin {

    private String visitorName = null;
    private final ServerFacade server;

    public Prelogin(ServerFacade server) throws GeneralException {
        this.server = server;
    }

    public String login(String... params) throws GeneralException {
        if(params.length == 2){

            String loggedIn = server.login(new LoginRequest(params[0], params[1]));
            visitorName = String.join("-", loggedIn);
            return String.format("LOGGED_IN AS %s", visitorName);
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password>");
    }

    public String register(String... params) throws GeneralException{
        if(params.length == 3){
            String registered = server.register(new RegisterRequest(params[0], params[1], params[2]));
            visitorName = String.join("-", registered);
            return String.format("LOGGED_IN AS %s", visitorName);
        }
        throw new GeneralException(GeneralException.ExceptionType.invalid, "Expected: <username> <password> <email>");
    }

}
