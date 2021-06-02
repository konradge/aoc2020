import Helpers.IO;

public class Day3 implements Day {
    String[][] pattern;
    
    public void prepare(String input){
        pattern = IO.get2DStringArraySplitByNewline(input);
    }

    int traverse(int dx, int dy) {
        
        int y = 0, x = 0, counter = 0;
        while (y < pattern.length) {
            x = (x + dx) % pattern[y].length;
            y += dy;
            if (y < pattern.length && pattern[y][x].equals("#")) {
                counter++;
            }
        }
        return counter;
    }

    public String partOne() {
        return "" + traverse(3, 1);
    }


    public String partTwo() {
        return traverse(1, 1) * traverse(3, 1) * traverse(5, 1) * traverse(7, 1) * traverse(1, 2) + "";
    }
}
