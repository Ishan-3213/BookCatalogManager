package CustomExceptions;

public class BadYearException extends Exception {
    public BadYearException(String message) {
        super(message);
    }
}