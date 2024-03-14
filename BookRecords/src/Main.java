import doPart1.DoPartOne;
import doPart2.DoPartTwo;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            DoPartOne doPartOne = new DoPartOne();
            DoPartTwo doPartTwo = new DoPartTwo();

            doPartOne.do_part1();
            doPartTwo.do_part2();
            do_part3();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void do_part3() throws IOException, ClassNotFoundException {
        // Implement reading binary files, deserializing array objects in each file,
        // and providing an interactive program for user navigation
        // Handle errors as required
    }

}