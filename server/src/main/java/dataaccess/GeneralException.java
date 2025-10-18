package dataaccess;

public class GeneralException extends Exception {

    private final String type;

    public GeneralException(String type, String message) {
        super(message);
        this.type = type;
    }
}
