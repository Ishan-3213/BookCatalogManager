// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------
package CustomExceptions;

/**
 * This class represents a custom exception for missing fields.
 * It extends the Exception class.
 */
public class MissingFieldException extends Exception {

    /**
     * Constructs a new MissingFieldException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public MissingFieldException(String message) {
        super(message);
    }
}
