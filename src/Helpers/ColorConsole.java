package Helpers;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Map.entry;

public class ColorConsole {

    private static Map<String, Integer> colors = Map.ofEntries(entry("r", 31), entry("g", 32), entry("b", 34),
            entry("y", 33), entry("c", 36), entry("w", 37), entry("p", 35), entry("/", 0));

    static String[] test = new String[]{"null", "eins", "zwei", "drei", "vier", "fünf"};

    public static void main(String[] args) {
        print("as/r dfg/b blau!!");
        System.out.println("\u001b[32m greeen");
        printStars("/r", "/g", "Hello World", 20);
    }

    /**
     * Use \r (red) \g (green) \b (blue), \y (yellow), \c (cyan), \w (white) \d
     * (default/clear)
     *
     * @param s
     */
    public static void print(String s) {
        StringBuffer res = new StringBuffer();
        Matcher matcher = Pattern.compile("/([rgbycw/])(ed|reen|lue|ellow|yan|hite)?").matcher(s);
        while (matcher.find()) {
            matcher.appendReplacement(res, "\u001B[" + colors.get(matcher.group(1)) + "m");
        }
        matcher.appendTail(res);
        System.out.print(res);
        //System.out.println(s.replaceAll(pattern, "" + "$1".charAt(0) + "a$1a"));
    }

    public static void printStars(String color1, String color2, String text, int length) {
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < length / 2; i++) {
                if (i % 2 == 0) {
                    s.append(color1).append("*");
                } else {
                    s.append(color2).append("*");
                }
            }
            if (j == 0) {
                s.append("// ").append(text).append(" ");
            }
        }
        print(s.append("\n").toString());
    }
}
