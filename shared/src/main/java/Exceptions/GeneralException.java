package Exceptions;

public class GeneralException extends Exception {

    public enum ExceptionType {
           invalid,
           unauthorized,
           forbidden,
           server
    }

    private final ExceptionType exceptionType;

    public GeneralException(ExceptionType exceptionType, String message) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public int getType(){
        return statusCode();
    }

    @Override
    public String getMessage() {
        return statusCode()+super.getMessage();
    }

    public int statusCode(){
        return switch (exceptionType){
            case invalid -> 400;
            case unauthorized -> 401;
            case forbidden -> 403;
            case server -> 500;
        };
    }

}
