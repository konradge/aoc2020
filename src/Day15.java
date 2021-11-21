import java.util.Arrays;
import java.util.HashMap;

public class Day15 implements Day {
    HashMap<Integer, int[]> lastSpoken = new HashMap<>();
    int turn = 0;
    int lastNum = 0;

    @Override
    public void prepare(String input) {
        for (int i = 0; i < input.split(",").length; i++) {
            lastNum = Integer.parseInt(input.trim().split(",")[i]);
            lastSpoken.put(lastNum, new int[]{-1, turn++});
        }
    }

    @Override
    public String partOne() {
        while (turn < 30000000) {
            if (lastSpoken.get(lastNum)[0] == -1) {
                int[] lastSayings = lastSpoken.get(0);
                lastSpoken.put(0, new int[]{lastSayings == null ? -1 : lastSayings[1], turn++});
                lastNum = 0;
            } else {
                int diff = lastSpoken.get(lastNum)[1] - lastSpoken.get(lastNum)[0];
                int[] lastSayings = lastSpoken.get(diff);
                lastSpoken.put(diff, new int[]{lastSayings == null ? -1 : lastSayings[1], turn++});
                lastNum = diff;
            }
            //System.out.println("Speaking " + lastNum + " in Turn " + turn);
        }
        return lastNum + "";
    }

    @Override
    public String partTwo() {
        return "";
    }
}
