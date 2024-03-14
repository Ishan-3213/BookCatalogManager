package doPart3;

import doPart2.Book;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DoPartThree {
    private static ArrayList<Book> selectedFile = new ArrayList<>();
    static String preFix = "../BookCatalogManager/BookRecords/src/Outputs/PartTwo/";
    private static int currentIndex = 0;
    public static String[] fileNames = {
            "Cartoons_Comics_Books.csv.txt.ser",
            "Hobbies_Collectibles_Books.csv.txt.ser",
            "Movies_TV.csv.txt.ser",
            "Music_Radio_Books.csv.txt.ser",
            "Nostalgia_Eclectic_Books.csv.txt.ser",
            "Old_Time_Radio.csv.txt.ser",
            "Sports_Sports_Memorabilia.csv.txt.ser",
            "Trains_Planes_Automobiles.csv.txt.ser"
    };
    public void do_part3() {
        loadArraysFromBinaryFiles();

        // Interactive menu loop
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("-----------------------------");
            System.out.println("Main Menu");
            System.out.println("-----------------------------");
            System.out.println("v View the selected file: " + getSelectedFileName() + " (" + selectedFile.size() + " records)");
            System.out.println("s Select a file to view");
            System.out.println("x Exit");
            System.out.print("Enter Your Choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "v":
                    viewSelectedFile();
                    break;
                case "s":
                    selectFile(scanner);
                    break;
                case "x":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadArraysFromBinaryFiles() {
        for (String fileName : fileNames) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(preFix+fileName))) {
                // Read array of Book objects from binary file
                Book[] books = (Book[]) ois.readObject();
                // Add the array to selectedFiles ArrayList
                for (Book book : books){
                    selectedFile.add(book);
                }
            } catch (IOException | ClassNotFoundException e) {
                // Handle exceptions
                e.printStackTrace();
            }
        }
    }

    private static String getSelectedFileName() {
        // Return the name of the currently selected file
        // For demonstration purposes, let's assume the file names are hard-coded
        return fileNames[currentIndex];
    }

    private static void selectFile(Scanner scanner) {
        System.out.println("-----------------------------");
        System.out.println("File Sub-Menu");
        System.out.println("-----------------------------");
        for (int i = 0; i < fileNames.length; i++) {
            System.out.println((i + 1) + " " + fileNames[i] + " (" + getRecordCount(i) + " records)");
        }
        System.out.println((fileNames.length + 1) + " Exit");
        System.out.print("Enter Your Choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice >= 1 && choice <= fileNames.length) {
            currentIndex = choice - 1;
            System.out.println("Selected file: " + getSelectedFileName());
        } else if (choice == fileNames.length + 1) {
            System.out.println("Exiting file selection...");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static int getRecordCount(int index) {
        // Simulate getting the record count for a selected file
        // This could be implemented based on the actual file data
        // For demonstration purposes, let's return some arbitrary values
        int[] recordCounts = {4, 1, 6, 13, 5, 0, 0, 2};
        if (index >= 0 && index < recordCounts.length) {
            return recordCounts[index];
        } else {
            // Return a default value or handle the case as needed
            return 0; // For simplicity, return 0 if index is out of bounds
        }
    }


    public static void viewSelectedFile() {
        Scanner scanner = new Scanner(System.in);
        int pageSize = 5; // Number of records to display at a time

        System.out.println("Viewing: " + getSelectedFileName() + " (" + selectedFile.size() + " records)");

        while (true) {
            System.out.println("Enter +n to view the next n records, -n to view the previous n records, or 0 to exit:");
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (choice == 0) {
                System.out.println("Exiting view...");
                break;
            } else if (choice > 0) {
                // Display next n records
                displayRecords(currentIndex, currentIndex + choice - 1, pageSize);
                currentIndex += choice;
            } else {
                // Display previous |choice| records
                displayRecords(currentIndex + choice, currentIndex - 1, pageSize);
                currentIndex += choice;
            }
            if (currentIndex < 0) {
                System.out.println("BOF (Beginning of File) has been reached.");
                currentIndex = 0;
            } else if (currentIndex >= selectedFile.size()) {
                System.out.println("EOF (End of File) has been reached.");
                currentIndex = selectedFile.size() - 1;
            }
        }
    }

    private static void displayRecords(int start, int end, int pageSize) {
        start = Math.max(start, 0);
        end = Math.min(end, selectedFile.size() - 1);
        for (int i = start; i <= end; i++) {
            System.out.println(selectedFile.get(i));
        }
    }
}
