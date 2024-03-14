package CustomExceptions;

public class BadPriceException extends Exception {
    public BadPriceException(String message) {
        super(message);
    }
}