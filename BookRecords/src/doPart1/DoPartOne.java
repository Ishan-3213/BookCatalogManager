package doPart1;
import CustomExceptions.*;
import Genre.Genres;

import java.io.*;

public class DoPartOne {

    public void do_part1() {
        BufferedReader inputFileNamesReader = null;
        FileWriter syntaxErrorFileWriter = null;
        FileWriter semanticErrorWriter = null;
        try {
            inputFileNamesReader = new BufferedReader(new FileReader("../BookCatalogManager/BookRecords/src/Comp6481_W24_Assg2-Needed-Files/part1_input_file_names.txt"));
            syntaxErrorFileWriter = new FileWriter("../BookCatalogManager/BookRecords/src/Outputs/syntax_error_file.txt");
            semanticErrorWriter = new FileWriter("../BookCatalogManager/BookRecords/src/Outputs/semantic_error_file.txt");

            int numFiles = Integer.parseInt(inputFileNamesReader.readLine());

            for (int i = 0; i < numFiles; i++) {
                String fileName = inputFileNamesReader.readLine();
                processInputFile(fileName, syntaxErrorFileWriter, semanticErrorWriter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputFileNamesReader != null) {
                    inputFileNamesReader.close();
                }
                if (syntaxErrorFileWriter != null) {
                    syntaxErrorFileWriter.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void processInputFile(String fileName, FileWriter syntaxErrorFileWriter, FileWriter semanticErrorWriter) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("../BookCatalogManager/BookRecords/src/Comp6481_W24_Assg2-Needed-Files/"+fileName))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                try {
                    validateSyntax(line);
                    validateAndPartitionBookRecord(line);
                }catch (SementicException e){
                    writeSemanticError(semanticErrorWriter, fileName, e.getMessage(), line);
                }catch (TooManyFieldsException | TooFewFieldsException | MissingFieldException |
                         UnknownGenreException e) {
                    writeSyntaxError(syntaxErrorFileWriter, fileName, e.getMessage(), line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName+"\n");
        }catch (IOException e) {
            e.getMessage();
        }
    }

    private String[] getFields(String line) {
        String[] tempFields = line.split(",");
        String title= tempFields[0];
        int count = 0;
        if (title.startsWith("\"")){
            for (count=1; count<tempFields.length; count++){
                if (tempFields[count].endsWith("\"")){
                    title +=", "+tempFields[count];
                    count++;
                    break;
                }
                title += tempFields[count];
            }
        }
        int One = 0;
        if (count<tempFields.length && count!=0) One = 1;
        String [] fields = new String[tempFields.length-count+One];
        if (count!=0 && count != tempFields.length){
            fields[0] = title;
            int fieldsCount = 1;
            for (int i = count; i<tempFields.length; i++){
                fields[fieldsCount] = tempFields[i];
                fieldsCount++;
            }
        }
        else fields = tempFields;
        return fields;
    }

    public  void validateSyntax(String line) throws TooManyFieldsException, TooFewFieldsException, MissingFieldException, UnknownGenreException {
        String[] fields = getFields(line);
        if (fields.length > 6) {
            throw new TooManyFieldsException("Too many fields");
        } else if (fields.length < 6) {
            throw new TooFewFieldsException("Too few fields");
        }
        for (String field : fields) {
            if (field.isEmpty()) {
                throw new MissingFieldException("Missing field");
            }
        }
        String genre = fields[4].trim();
        if (isValidGenre(genre) == null) {
            throw new UnknownGenreException("Invalid genre: " + genre);
        }
    }

    public  void validateAndPartitionBookRecord(String line) throws SementicException {
            String[] fields = getFields(line);
            String title = fields[0].trim();
            String authors = fields[1].trim();
            double price = Double.parseDouble(fields[2].trim());
            String isbn = fields[3].trim();
            String genre = isValidGenre(fields[4].trim());
            int year = Integer.parseInt(fields[5].trim());

            validatePrice(price);
            validateYear(year);
            if (isValidISBN(isbn)){}
            else
                if (isbn.length()<11) throw new SementicException("Invalid-ISBN10 value: " + isbn);
                else throw new SementicException("Invalid-ISBN13 value: " + isbn);
            partitionBasedOnGenre(line, genre);
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


    public  void validatePrice(double price) throws SementicException {
        if (price < 0) {
            throw new SementicException("Invalid price: " + price);
        }
    }

    public  void validateYear(int year) throws SementicException {
        if (year < 1995 || year > 2024) {
            throw new SementicException("Invalid year: " + year);
        }
    }

    public  void partitionBasedOnGenre(String line, String genre) {
        try (FileWriter writer = new FileWriter("../BookCatalogManager/BookRecords/src/Outputs/"+genre + ".txt", true)) {
            writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  String isValidGenre(String genre) {
        for (Genres gnr : Genres.values()) {
            if (gnr.name().equals(genre)) {
                return gnr.getAssociatedFileName();
            }
        }
        return null;
    }

    public  void writeSyntaxError(FileWriter writer, String fileName, String error, String line) throws IOException {
        writer.write("syntax error in file: " + fileName + "\n");
        writer.write("====================\n");
        writer.write("Error: " + error + "\n");
        writer.write("Record: " + line + "\n\n");
    }

    public void writeSemanticError(FileWriter semanticErrorWriter, String fileName, String message, String line) throws IOException {
        semanticErrorWriter.write("semantic error in file: " + fileName + "\n");
        semanticErrorWriter.write("====================\n");
        semanticErrorWriter.write("Error: " + message + "\n");
        semanticErrorWriter.write("Record: " + line + "\n\n");
    }

}
