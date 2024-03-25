// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------

package CustomExceptions;

/**
 * Custom exception class for handling bad ISBN-13 numbers.
 * This exception is thrown when an invalid ISBN-13 number is encountered.
 */
public class BadIsbn13Exception extends Exception {

    /**
     * Constructs a new BadIsbn13Exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BadIsbn13Exception(String message) {
        super(message);
    }
}
