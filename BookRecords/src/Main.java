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

public class Main {

    public static void clearFiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.BASE_PATH_FOR_INPUT_2  + Constants.SEMANTIC_ERROR_FILE))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error occurred while clearing the file: " + Constants.SEMANTIC_ERROR_FILE + " " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.BASE_PATH_FOR_INPUT_1  + Constants.SYNTAX_ERROR_FILE))) {
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
            }try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName_2))) {
                writer.write("");
            } catch (IOException e) {
                System.err.println("Error occurred while clearing the file: " + e.getMessage());
            }
        }
    }

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