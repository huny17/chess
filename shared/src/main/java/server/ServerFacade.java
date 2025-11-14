package server;

import Exceptions.*;
import com.google.gson.Gson;
import model.*;
import model.request.CreateGameRequest;
import model.request.JoinGameRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    private AuthData token;

    public ServerFacade(String url){
        serverUrl = url;
    }

    public String register(RegisterRequest req) throws GeneralException{
        var request = buildRequest("POST", "/user", req);
        var response = sendRequest(request);
        token = handleResponse(response, AuthData.class);
        return token.username();
    }

    public String login(LoginRequest req) throws GeneralException{
        var request = buildRequest("POST", "/session", req);
        var response = sendRequest(request);
        token = handleResponse(response, AuthData.class);
        return token.username();
    }

    public String logout() throws GeneralException{
        var request = buildRequest("DELETE", "/session", null);
        var response = sendRequest(request);
        String user = token.username();
        handleResponse(response, AuthData.class);
        token = null;
        return user;
    }

    public GameData createGame(CreateGameRequest req) throws GeneralException{
        var request = buildRequest("POST", "/game", req);
        var response = sendRequest(request);
        GameData game = handleResponse(response, GameData.class);
        return game;
    }

    public GameData joinGame(JoinGameRequest req) throws GeneralException{
        var request = buildRequest("PUT", "/game", req);
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }


    public List<GameData> listGames() throws GeneralException{
        var request = buildRequest("GET", "/game", null);
        var response = sendRequest(request);
        return handleResponse(response, List.class);
    }

    public UserData clear() throws GeneralException{
        var request = buildRequest("DELETE", "/db", null);
        var response = sendRequest(request);
        return handleResponse(response, UserData.class);
    }

    private HttpRequest buildRequest(String method, String path, Object body){
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if(body != null){
            request.setHeader("Content-Type", "application/json");
        }
        if(token != null){
            request.header("Authorization", token.authToken());
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request){
        if(request != null){
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws GeneralException {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            throw new GeneralException(GeneralException.ExceptionType.invalid, e.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws GeneralException{
        var status = response.statusCode();
        if(!(status == 200)){
            var body = response.body();
            if(body != null){
                throw new GeneralException(GeneralException.ExceptionType.invalid, body);
            }
            throw new GeneralException(GeneralException.ExceptionType.invalid, body);
        }
        if(responseClass != null){
            return new Gson().fromJson(response.body(), responseClass);
        }
        return null;
    }

}
