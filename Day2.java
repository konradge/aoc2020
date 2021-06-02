import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 implements Day {

    String beginn = "(\\d+)-(\\d+) (\\w): ";
    String[] lines;
    
    public void prepare(String input){
        String[] lines = input.split("\n");
    }

    @Override
    public String partOne() {
        int res1 = 0;
        for (String line : lines) {
            Matcher minMax = Pattern.compile(beginn).matcher(line);
            minMax.find();
            int min = Integer.parseInt(minMax.group(1));
            int max = Integer.parseInt(minMax.group(2));
            String c = minMax.group(3);
            int diff = line.length() - line.replace(c, "").length() - 1;
            if (diff >= min && diff <= max) {
                res1++;
            }
        }
        return res1 + "";
    }

    @Override
    public String partTwo() {
        int res2 = 0;
        for (String line : lines) {
            Matcher minMax = Pattern.compile(beginn).matcher(line);
            minMax.find();
            int min = Integer.parseInt(minMax.group(1));
            int max = Integer.parseInt(minMax.group(2));
            String c = minMax.group(3);
            String first = "" + line.split(": ")[1].charAt(min - 1);
            String second = "" + line.split(": ")[1].charAt(max - 1);
            if (first.equals(c) && !second.equals(c) || second.equals(c) && !first.equals(c)) {
                res2++;
            }
        }
        return res2 + "";
    }
}
