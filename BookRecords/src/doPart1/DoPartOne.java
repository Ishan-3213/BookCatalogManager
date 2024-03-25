// -----------------------------------------------------
// Assignment - 2
// Question: 1 Part 1.
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------
package doPart1;

import CustomExceptions.*;
import Genre.Genres;
import doPart2.*;
import java.io.*;

/**
 * This class represents the implementation of Part 1 of Question 1.
 * It reads input files, validates syntax, processes book records, and partitions them based on genre.
 */
public class DoPartOne {

    // Prefix for input and output file paths
    static String inputPrefix = "../BookCatalogManager/BookRecords/src/Comp6481_W24_Assg2-Needed-Files/";
    static String outputPrefix = "../BookCatalogManager/BookRecords/src/Outputs/PartOne/";

    /**
     * Reads input files and processes them.
     */
    public void do_part1() {
        BufferedReader inputFileNamesReader = null;
        FileWriter syntaxErrorFileWriter = null;
        try {
            // Read input file containing file names
            inputFileNamesReader = new BufferedReader(new FileReader(inputPrefix + "part1_input_file_names.txt"));
            syntaxErrorFileWriter = new FileWriter(outputPrefix + "syntax_error_file.txt");

            int numFiles = Integer.parseInt(inputFileNamesReader.readLine());

            // Process each input file
            for (int i = 0; i < numFiles; i++) {
                String fileName = inputFileNamesReader.readLine();
                processInputFile(fileName, syntaxErrorFileWriter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close readers and writers
            try {
                if (inputFileNamesReader != null) {
                    inputFileNamesReader.close();
                }
                if (syntaxErrorFileWriter != null) {
                    syntaxErrorFileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Processes an input file.
     *
     * @param fileName               The name of the input file to process.
     * @param syntaxErrorFileWriter  The FileWriter for the syntax error file.
     */
    public void processInputFile(String fileName, FileWriter syntaxErrorFileWriter) {
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(inputPrefix + fileName));
            String line;
            while ((line = fileReader.readLine()) != null) {
                try {
                    // Validate syntax and partition book records
                    validateSyntax(line);
                    validateAndPartitionBookRecord(line);
                } catch (TooManyFieldsException | TooFewFieldsException | MissingFieldException |
                         UnknownGenreException e) {
                    writeSyntaxError(syntaxErrorFileWriter, fileName, e.getMessage(), line);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName + "\n");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Validates the syntax of a line in a book record.
     *
     * @param line  The line to validate.
     * @throws TooManyFieldsException   If there are too many fields in the line.
     * @throws TooFewFieldsException    If there are too few fields in the line.
     * @throws MissingFieldException    If any required field is missing.
     * @throws UnknownGenreException    If the genre is unknown.
     */
    public void validateSyntax(String line) throws TooManyFieldsException, TooFewFieldsException,
            MissingFieldException, UnknownGenreException {
        String[] fields = Book.getFields(line);
        if (fields.length > 6) {
            throw new TooManyFieldsException("Too many fields");
        } else if (fields.length < 6) {
            throw new TooFewFieldsException("Too few fields");
        }
        String title = fields[0];
        String authors = fields[1];
        String price = fields[2];
        String isbn = fields[3];
        String genre = fields[4];
        String year = fields[5];

        String missingFields = "";
        if (title.isEmpty()) {
            missingFields += "Title, ";
        }
        if (authors.isEmpty()) {
            missingFields += "Authors, ";
        }
        if (price.isEmpty()) {
            missingFields += "Price, ";
        }
        if (isbn.isEmpty()) {
            missingFields += "ISBN, ";
        }
        if (genre.isEmpty()) {
            missingFields += "Genre, ";
        }
        if (year.isEmpty()) {
            missingFields += "Year, ";
        }

        if (!missingFields.isEmpty()) {
            missingFields = missingFields.substring(0, missingFields.length() - 2);
            throw new MissingFieldException("Missing field: " + missingFields);
        }
        if (Genres.getAssociatedFileName(genre) == null) {
            throw new UnknownGenreException("Invalid genre: " + genre);
        }
    }

    /**
     * Validates and partitions a book record based on its genre.
     *
     * @param line  The line containing the book record.
     * @throws IOException  If an I/O error occurs.
     */
    public void validateAndPartitionBookRecord(String line) throws IOException {
        String[] fields = Book.getFields(line);
        String genre = Genres.getAssociatedFileName(fields[4].trim());
        partitionBasedOnGenre(line, genre);
    }

    /**
     * Partitions a book record based on its genre.
     *
     * @param line   The line containing the book record.
     * @param genre  The genre of the book record.
     */
    public void partitionBasedOnGenre(String line, String genre) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputPrefix + genre, true);
            writer.write(line + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes syntax error details to the syntax error file.
     *
     * @param writer    The FileWriter for the syntax error file.
     * @param fileName  The name of the input file where the error occurred.
     * @param error     The error message.
     * @param line      The line where the error occurred.
     * @throws IOException  If an I/O error occurs.
     */
    public void writeSyntaxError(FileWriter writer, String fileName, String error, String line) throws IOException {
        writer.write("syntax error in file: " + fileName + "\n");
        writer.write("====================\n");
        writer.write("Error: " + error + "\n");
        writer.write("Record: " + line + "\n\n");
    }
}
