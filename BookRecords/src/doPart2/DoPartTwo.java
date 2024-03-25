// -----------------------------------------------------
// Assignment - 2
// Question: 2 Part 2.
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------
package doPart2;

import CustomExceptions.*;

import java.io.*;

/**
 * Represents the implementation of Part 2 of Question 2.
 * This class reads input files, processes book records, and handles semantic errors.
 */
public class DoPartTwo {
    static String preFix = "../BookCatalogManager/BookRecords/src/Outputs/PartTwo/";
    static String partOnePrefix = "../BookCatalogManager/BookRecords/src/Outputs/PartOne/";
    static int validLines = 0;

    /**
     * Performs Part 2 of Question 2.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void do_part2() throws IOException {
        String[] inputFiles = {
                "Cartoons_Comics_Books.csv.txt",
                "Hobbies_Collectibles_Books.csv.txt",
                "Movies_TV.csv.txt",
                "Music_Radio_Books.csv.txt",
                "Nostalgia_Eclectic_Books.csv.txt",
                "Old_Time_Radio.csv.txt",
                "Sports_Sports_Memorabilia.csv.txt",
                "Trains_Planes_Automobiles.csv.txt"
        };
        FileWriter semanticErrorWriter = null;
        BufferedReader lineCounter = null;
        try {
            semanticErrorWriter = new FileWriter(preFix + "semantic_error_file.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String inputFile : inputFiles) {
            try {
                lineCounter = new BufferedReader(new FileReader(partOnePrefix + inputFile));
                int lineCount = 0;
                while (lineCounter.readLine() != null) {
                    lineCount++;
                }
                lineCounter.close();
                BufferedReader reader = new BufferedReader(new FileReader(partOnePrefix + inputFile));
                Book[] books = new Book[lineCount];
                String line;
                int bookIndex = 0;
                while ((line = reader.readLine()) != null) {
                    try {
                        Book book = processLine(line);
                        // Add the book to the list of books
                        books[bookIndex] = book;
                        bookIndex++;
                        validLines = bookIndex;
                    } catch (BadIsbn10Exception | BadIsbn13Exception | BadPriceException | BadYearException e) {
                        // Log the semantic error to semantic_error_file.txt
                        writeSemanticError(semanticErrorWriter, inputFile, e.getMessage(), line);
                    }
                }
                reader.close();
                Book[] correctBooks;
                correctBooks = new Book[validLines];
                for (int i = 0; i < validLines; i++) {
                    correctBooks[i] = books[i];
                }

                // Serialize the array of Book objects into a binary file
                String outputFile = preFix + inputFile + ".ser";
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
                oos.writeObject(correctBooks);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        semanticErrorWriter.close();
    }

    /**
     * Validates the ISBN format.
     *
     * @param isbn The ISBN to validate.
     * @return True if the ISBN is valid, false otherwise.
     */
    public static boolean isValidISBN(String isbn) {
        // Remove any hyphens or spaces from the ISBN
        isbn = isbn.replaceAll("[-\\s]", "");

        if (isbn.length() == 10) {
            return isValidISBN10(isbn);
        } else if (isbn.length() == 13) {
            return isValidISBN13(isbn);
        }
        return false; // Invalid ISBN length
    }

    /**
     * Processes a line from the input file and creates a Book object.
     *
     * @param line The line to process.
     * @return The Book object created from the line.
     * @throws BadIsbn10Exception If the ISBN-10 is invalid.
     * @throws BadIsbn13Exception If the ISBN-13 is invalid.
     * @throws BadPriceException  If the price is invalid.
     * @throws BadYearException   If the year is invalid.
     */
    public static Book processLine(String line) throws BadIsbn10Exception, BadIsbn13Exception, BadPriceException, BadYearException {
        // Split the line by comma
        String[] data = Book.getFields(line);

        // Extract data fields from the line
        String title = data[0];
        String authors = data[1];
        double price = Double.parseDouble(data[2]);
        String isbn = data[3];
        String genre = data[4];
        int year = Integer.parseInt(data[5]);

        validatePrice(price);
        validateYear(year);
        if (!isValidISBN(isbn)) {
            if (isbn.length() < 11) throw new BadIsbn10Exception("Invalid-ISBN10 value: " + isbn);
            else throw new BadIsbn13Exception("Invalid-ISBN13 value: " + isbn);
        }

        return new Book(title, authors, price, isbn, genre, year);
    }

    // Validate a 10-digit ISBN
    private static boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            char digit = isbn.charAt(i);
            if (i == 9 && digit == 'X') {
                // If the last character is 'X', it represents the value 10
//                sum += 10;
            } else if (Character.isDigit(digit)) {
                // Multiply the digit by its position in the ISBN (from right to left) and accumulate
                sum += (10 - i) * Character.getNumericValue(digit);
            } else {
                return false; // Invalid character in ISBN
            }
        }

        // Check if the sum is divisible by 11
        return sum % 11 == 0;
    }

    // Validate a 13-digit ISBN
    private static boolean isValidISBN13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            char digit = isbn.charAt(i);
            if (Character.isDigit(digit)) {
                int value = Character.getNumericValue(digit);
                // Multiply the value of even positions by 3 and sum, else sum as it is
                sum += (i % 2 == 0) ? value : value * 3;
            } else {
                return false; // Invalid character in ISBN
            }
        }

        // Check if the sum is divisible by 10
        return sum % 10 == 0;
    }

    /**
     * Validates the price of a book.
     *
     * @param price The price to validate.
     * @throws BadPriceException If the price is negative.
     */
    public static void validatePrice(double price) throws BadPriceException {
        if (price < 0) {
            throw new BadPriceException("Invalid price: " + price);
        }
    }

    /**
     * Validates the year of publication of a book.
     *
     * @param year The year to validate.
     * @throws BadYearException If the year is not within the range of 1995 to 2024.
     */
    public static void validateYear(int year) throws BadYearException {
        if (year < 1995 || year > 2024) {
            throw new BadYearException("Invalid year: " + year);
        }
    }

    /**
     * Writes a semantic error message to a file.
     *
     * @param semanticErrorWriter The FileWriter object to write to.
     * @param fileName            The name of the file where the error occurred.
     * @param message             The error message.
     * @param line                The line where the error occurred.
     * @throws IOException If an I/O error occurs while writing the error message.
     */
    public static void writeSemanticError(FileWriter semanticErrorWriter, String fileName, String message, String line) throws IOException {
        semanticErrorWriter.write("semantic error in file: " + fileName + "\n");
        semanticErrorWriter.write("====================\n");
        semanticErrorWriter.write("Error: " + message + "\n");
        semanticErrorWriter.write("Record: " + line + "\n\n");
    }
}