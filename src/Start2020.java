import Helpers.ColorConsole;
import Helpers.IO;

import java.util.HashMap;

public class Start2020 {
    HashMap<String, Day> objects = new HashMap<>();
    String workingDir = System.getProperty("user.dir");

    public Start2020() {
        objects.put("1", new Day1());
        objects.put("2", new Day2());
        objects.put("3", new Day3());
        objects.put("7", new Day7());
        objects.put("8", new Day8());
        objects.put("9", new Day9());
        objects.put("10", new Day10());
        objects.put("11", new Day11());
        objects.put("12", new Day12());
        objects.put("13", new Day13());
        objects.put("14", new Day14());
        objects.put("15", new Day15());
        objects.put("16", new Day16());
        objects.put("17", new Day17());
        objects.put("18", new Day18());
        objects.put("19", new Day19());
        objects.put("20", new Day20());
        objects.put("21", new Day21());
        objects.put("22", new Day22());
        objects.put("23", new Day23());
    }

    public static void main(String[] args) {
        int day = 1;
        if (args.length >= 1) {
            day = Integer.parseInt(args[0]);
        } else {
            System.err.println("No day configured");
            return;
        }
        (new Start2020()).run(day);
    }

    public void run(int day) {
        long totalTime = 0;
        ColorConsole.printStars("/r", "/g", "AoC 2020, Day " + day, 40);
        ColorConsole.print("/wPreparing input...");
        long start = System.nanoTime();
        objects.get("" + day).prepare(IO.getInput("2020day" + day + ".txt"));
        long end = System.nanoTime();
        totalTime += (end - start);
        ColorConsole.print("\n/b Prepared in " + (end - start) + "ns" + " (" + Math.round((end - start) * 1e-6 * 10.0) / 10.0 + "ms)");
        ColorConsole.print("\n/r Part one://\n");
        start = System.nanoTime();
        ColorConsole.print(objects.get("" + day).partOne());
        end = System.nanoTime();
        totalTime += (end - start);
        ColorConsole.print("\n/b Duration: " + (System.nanoTime() - start) + "ns" + " (" + Math.round((end - start) * 1e-6 * 10.0) / 10.0 + "ms)");
        ColorConsole.print("\n/r Part two://\n");
        start = System.nanoTime();
        ColorConsole.print(objects.get("" + day).partTwo());
        end = System.nanoTime();
        totalTime += (end - start);
        ColorConsole.print("\n/b Duration: " + (System.nanoTime() - start) + "ns" + " (" + Math.round((end - start) * 1e-6 * 10.0) / 10.0 + "ms)");
        ColorConsole.print("\n/g Total Time: " + totalTime + "ns" + " (" + Math.round((totalTime) * 1e-6 * 10.0) / 10.0 + "ms)");
        ColorConsole.print("/w");
    }
}
