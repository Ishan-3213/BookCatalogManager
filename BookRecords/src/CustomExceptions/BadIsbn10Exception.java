// -----------------------------------------------------
// Assignment - 2
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// ----------------------------------------------------

package CustomExceptions;

/**
 * Custom exception class for handling bad ISBN-10 numbers.
 * This exception is thrown when an invalid ISBN-10 number is encountered.
 */
public class BadIsbn10Exception extends Exception {

    /**
     * Constructs a new BadIsbn10Exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BadIsbn10Exception(String message) {
        super(message);
    }
}
