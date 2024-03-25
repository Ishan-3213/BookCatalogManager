// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------

package CustomExceptions;

/**
 * Custom exception class for handling bad price values.
 * This exception is thrown when an invalid price value is encountered.
 */
public class BadPriceException extends Exception {

    /**
     * Constructs a new BadPriceException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BadPriceException(String message) {
        super(message);
    }
}
