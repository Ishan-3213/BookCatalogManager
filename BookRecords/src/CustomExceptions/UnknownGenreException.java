// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------
package CustomExceptions;

/**
 * This class represents a custom exception for an unknown genre.
 * It extends the Exception class.
 */
public class UnknownGenreException extends Exception {

    /**
     * Constructs a new UnknownGenreException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public UnknownGenreException(String message) {
        super(message);
    }
}
