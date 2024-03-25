// -----------------------------------------------------
// Assignment - 2
// COMP6481
// Written by: Ishan Pansuriya(40232841) && Tanmay Soni(40240650)
// Due Date: 24/03/2024
// ----------------------------------------------------

import Constant.Constants;
import doPart1.DoPartOne;
import doPart2.DoPartTwo;
import doPart3.DoPartThree;
import Genre.Genres;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static Genre.Genres.getAssociatedFileName;
/**
 * This class represents the main entry point of the program for Assignment 2 of COMP6481.
 * It contains methods to clear files and executes three parts of the assignment.
 */
public class Main {

    /**
     * Clears all necessary files before starting the program.
     * Clears semantic error and syntax error files, and files associated with each genre.
     */
    public static void clearFiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.BASE_PATH_FOR_INPUT_2 + Constants.SEMANTIC_ERROR_FILE))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error occurred while clearing the file: " + Constants.SEMANTIC_ERROR_FILE + " " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.BASE_PATH_FOR_INPUT_1 + Constants.SYNTAX_ERROR_FILE))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error occurred while clearing the file: " + Constants.SYNTAX_ERROR_FILE + " " + e.getMessage());
        }

        for (Genres genre : Genres.values()) {
            String gen = getAssociatedFileName(String.valueOf(genre));
            String fileName_1 = Constants.BASE_PATH_FOR_INPUT_2 + gen + ".ser";
            String fileName_2 = Constants.BASE_PATH_FOR_INPUT_1 + gen;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName_1))) {
                writer.write("");
            } catch (IOException e) {
                System.err.println("Error occurred while clearing the file: " + e.getMessage());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName_2))) {
                writer.write("");
            } catch (IOException e) {
                System.err.println("Error occurred while clearing the file: " + e.getMessage());
            }
        }
    }

    /**
     * The main method where the program execution starts.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            clearFiles();
            DoPartOne doPartOne = new DoPartOne();
            DoPartTwo doPartTwo = new DoPartTwo();
            DoPartThree doPartThree = new DoPartThree();

            doPartOne.do_part1();
            doPartTwo.do_part2();
            doPartThree.do_part3();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
