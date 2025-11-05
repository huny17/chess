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

    public ServerFacade(String url){
        serverUrl = url;
    }

    public Collection<GameData> listGames() throws Exception{
        var request = buildRequest("GET", "", ):
        var response = sendRequest(request);
        return handleResponse(response, )
    }

    private HttpRequest buildRequest(String method, String path, Object body){
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if(body != null){
            request.setHeader("Content-Type", "application/json");
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
        if(!isSuccessful(status)){
            var body = response.body();
            if(body != null){
                throw Exception.fromJson(body);
            }
            throw new Exception();
        }

    }

}
