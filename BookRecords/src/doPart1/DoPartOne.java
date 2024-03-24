package doPart1;
import CustomExceptions.*;
import Genre.Genres;
import doPart2.*;
import java.io.*;

public class DoPartOne {
    static String inputPrefix = "../BookCatalogManager/BookRecords/src/Comp6481_W24_Assg2-Needed-Files/";
    static String outputPrefix = "../BookCatalogManager/BookRecords/src/Outputs/PartOne/";
    public void do_part1() {
        BufferedReader inputFileNamesReader = null;
        FileWriter syntaxErrorFileWriter = null;
        try {
            inputFileNamesReader = new BufferedReader(new FileReader(inputPrefix +"part1_input_file_names.txt"));
            syntaxErrorFileWriter = new FileWriter(outputPrefix+"syntax_error_file.txt");

            int numFiles = Integer.parseInt(inputFileNamesReader.readLine());

            for (int i = 0; i < numFiles; i++) {
                String fileName = inputFileNamesReader.readLine();
                processInputFile(fileName, syntaxErrorFileWriter);
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

    public  void processInputFile(String fileName, FileWriter syntaxErrorFileWriter) throws IOException {
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(inputPrefix+fileName));
            String line;
            while ((line = fileReader.readLine()) != null) {
                try {
                    validateSyntax(line);
                    validateAndPartitionBookRecord(line);
                }catch (TooManyFieldsException | TooFewFieldsException | MissingFieldException |
                         UnknownGenreException e) {
                    writeSyntaxError(syntaxErrorFileWriter, fileName, e.getMessage(), line);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName+"\n");
        }catch (IOException e) {
            e.getMessage();
        }
    }
    public  void validateSyntax(String line) throws TooManyFieldsException, TooFewFieldsException, MissingFieldException, UnknownGenreException {
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

    public void validateAndPartitionBookRecord(String line) throws IOException {
            String[] fields = Book.getFields(line);
            String genre = Genres.getAssociatedFileName(fields[4].trim());
            partitionBasedOnGenre(line, genre);
    }


    public  void partitionBasedOnGenre(String line, String genre){
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputPrefix+genre, true);
            writer.write(line + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void writeSyntaxError(FileWriter writer, String fileName, String error, String line) throws IOException {
        writer.write("syntax error in file: " + fileName + "\n");
        writer.write("====================\n");
        writer.write("Error: " + error + "\n");
        writer.write("Record: " + line + "\n\n");
    }

}
