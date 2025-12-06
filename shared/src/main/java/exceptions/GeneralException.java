package exceptions;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class GeneralException extends Exception {

    public enum ExceptionType {
           invalid,
           unauthorized,
           forbidden,
           server
    }

    private final ExceptionType type;

    public GeneralException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public GeneralException(ExceptionType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public int getType(){
        return statusCode();
    }

    public String toJson(){
        return new Gson().toJson(Map.of("message", getMessage(), "Status", statusCode()));
    }

    public static GeneralException fromJson(String json, int status){
        var map = new Gson().fromJson(json, HashMap.class);
        String message = map.get("message").toString();
        return new GeneralException(convertStatus(status), message);
    }

    public int statusCode(){
        return switch (type){
            case invalid -> 400;
            case unauthorized -> 401;
            case forbidden -> 403;
            case server -> 500;
        };
    }

    public static ExceptionType convertStatus(int status){
        return switch (status){
            case 400 -> GeneralException.ExceptionType.invalid;
            case 401 -> GeneralException.ExceptionType.unauthorized;
            case 403 -> GeneralException.ExceptionType.forbidden;
            case 500 -> GeneralException.ExceptionType.server;
            default -> throw new IllegalArgumentException("Unknown HTTP status code: " + status);
        };
    }

}
