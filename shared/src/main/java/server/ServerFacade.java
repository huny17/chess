package server;

import com.google.gson.Gson;
import model.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collection;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    private AuthData token;

    public ServerFacade(String url){
        serverUrl = url;
    }

    public UserData register(UserData user) throws Exception{
        var request = buildRequest("POST", "/user", user);
        var response = sendRequest(request);
        return handleResponse(response, UserData.class);
    }

    public String login(String... params) throws Exception{
        var request = buildRequest("POST", "/session", params);
        var response = sendRequest(request);
        token = handleResponse(response, AuthData.class);
        return token.username();
    }

    public String logout() throws Exception{
        var request = buildRequest("DELETE", "/session", null);
        var response = sendRequest(request);
        token = handleResponse(response, AuthData.class);
        return token.username();
    }

    public GameData createGame(String... params) throws Exception{
        var request = buildRequest("POST", "/game", params);
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }

    public GameData joinGame(String... params) throws Exception{
        var request = buildRequest("PUT", "/game", params);
        var response = sendRequest(request);
        return handleResponse(response, GameData.class);
    }


    public Collection<GameData> listGames() throws Exception{
        var request = buildRequest("GET", "/game", null);
        var response = sendRequest(request);
        return handleResponse(response, Collection.class);
    }

    public UserData clear() throws Exception{
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

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception{
        try{
            return client.send(request, BodyHandlers.ofString());
        }catch (Exception e){
            throw new Exception();
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws Exception{
        var status = response.statusCode();
        if(!(status == 200)){
            var body = response.body();
            if(body != null){
                throw new Exception();
            }
            throw new Exception();
        }
        if(responseClass != null){
            return new Gson().fromJson(response.body(), responseClass);
        }
        return null;
    }

}
