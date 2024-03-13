import doPart1.DoPartOne;
import java.io.IOException;



public class Main {

    public static void main(String[] args) {
        try {
            DoPartOne doPartOne = new DoPartOne();
            doPartOne.do_part1();
            do_part2();
            do_part3();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void do_part2() throws IOException {
        // Implement semantic validation, read genre files into arrays of Book objects,
        // and serialize the arrays of Book objects into binary files
        // Handle errors as required
    }

    private static void do_part3() throws IOException, ClassNotFoundException {
        // Implement reading binary files, deserializing array objects in each file,
        // and providing an interactive program for user navigation
        // Handle errors as required
    }

}