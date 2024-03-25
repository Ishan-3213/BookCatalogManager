// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------

package CustomExceptions;
/**
 * Custom exception class for handling bad year inputs.
 * Extends the Exception class.
 */
public class BadYearException extends Exception {

    /**
     * Constructor for BadYearException class.
     * @param message A String describing the reason for the exception.
     */
    public BadYearException(String message) {
        super(message);
    }
}
