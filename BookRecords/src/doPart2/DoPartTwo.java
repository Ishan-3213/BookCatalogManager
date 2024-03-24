package doPart2;
import CustomExceptions.*;
import java.io.*;

public class DoPartTwo {
    static String preFix = "../BookCatalogManager/BookRecords/src/Outputs/PartTwo/";
    static String partOnePrefix = "../BookCatalogManager/BookRecords/src/Outputs/PartOne/";
    static int validLines = 0;

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
            semanticErrorWriter = new FileWriter(preFix+"semantic_error_file.txt");
        }catch (IOException e){
            e.printStackTrace();
        }
        for (String inputFile : inputFiles) {
            try {
                lineCounter = new BufferedReader(new FileReader(partOnePrefix + inputFile));
                int lineCount = 0;
                while (lineCounter.readLine()!=null){
                    lineCount++;
                }
                lineCounter.close();
                BufferedReader reader = new BufferedReader(new FileReader(partOnePrefix+inputFile));
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
//                if (validLines != bookIndex){
                    correctBooks = new Book[validLines];
                    for(int i = 0; i < validLines; i++) {
                        correctBooks[i] = books[i];
                    }
//                }else {
//                    correctBooks = books;
//                }

                // Serialize the array of Book objects into a binary file
                String outputFile = preFix+inputFile + ".ser";
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
                oos.writeObject(correctBooks);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        semanticErrorWriter.close();
    }

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
        if (isValidISBN(isbn)){}
        else
            if (isbn.length()<11) throw new BadIsbn10Exception("Invalid-ISBN10 value: " + isbn);
            else throw new BadIsbn13Exception("Invalid-ISBN13 value: " + isbn);

        return new Book(title, authors, price, isbn, genre, year);
    }

    // Validate a 10-digit ISBN
    private static boolean isValidISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            char digit = isbn.charAt(i);
            if (i == 9 && digit == 'X') {
                sum += 10;
            } else if (Character.isDigit(digit)) {
                sum += (10 - i) * Character.getNumericValue(digit);
            } else {
                return false; // Invalid character in ISBN
            }
        }

        return sum % 11 == 0;
    }

    // Validate a 13-digit ISBN
    private static boolean isValidISBN13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            char digit = isbn.charAt(i);
            if (Character.isDigit(digit)) {
                int value = Character.getNumericValue(digit);
                sum += (i % 2 == 0) ? value : value * 3;
            } else {
                return false; // Invalid character in ISBN
            }
        }

        return sum % 10 == 0;
    }
    public static void validatePrice(double price) throws BadPriceException {
        if (price < 0) {
            throw new BadPriceException("Invalid price: " + price);
        }
    }

    public static void validateYear(int year) throws BadYearException {
        if (year < 1995 || year > 2024) {
            throw new BadYearException("Invalid year: " + year);
        }
    }
    public static void writeSemanticError(FileWriter semanticErrorWriter, String fileName, String message, String line) throws IOException {
        semanticErrorWriter.write("semantic error in file: " + fileName + "\n");
        semanticErrorWriter.write("====================\n");
        semanticErrorWriter.write("Error: " + message + "\n");
        semanticErrorWriter.write("Record: " + line + "\n\n");
    }
}