import doPart1.DoPartOne;
import doPart2.DoPartTwo;
import doPart3.DoPartThree;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
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