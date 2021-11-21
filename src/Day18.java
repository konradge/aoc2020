public class Day18 implements Day {
    String expString;

    @Override
    public void prepare(String input) {
        expString = input;
    }

    @Override
    public String partOne() {
        long sum = 0;
        for (String s : expString.split("\n")) {
            sum += initCalc(s);
        }
        return "" + sum;
    }

    @Override
    public String partTwo() {
        long sum = 0;
        for (String s : expString.split("\n")) {
            s = s.replaceAll("\\+", "#").replaceAll(" ", "");
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '#') {
                    s = replace(s, i, '+');
                    if (s.charAt(i - 1) == '(') {
                        s = insert(s, i - 1, '(');
                    } else if (Character.isDigit(s.charAt(i - 1))) {
                        s = insert(s, i - 2, '(');
                    } else {
                        int paranthesesDelta = calcParanthesesDelta(new StringBuilder(s.substring(0, i)).reverse().toString()
                                .replaceAll("\\(", "]").replaceAll("\\)", "["), 0);
                        s = insert(s, i - paranthesesDelta, '(');
                    }
                    i++;
                    if (s.charAt(i + 1) == ')') {
                        s = insert(s, i + 1, ')');
                    } else if (Character.isDigit(s.charAt(i + 1))) {
                        s = insert(s, i + 1, ')');
                    } else {
                        int paranthesesDelta = calcParanthesesDelta(s.substring(i + 1).replaceAll("\\(", "[").replaceAll("\\)", "]"), 0);
                        s = insert(s, i + paranthesesDelta, ')');
                    }

                }
            }
            sum += initCalc(s);
        }
        return "" + sum;
    }

    //inserts after i
    private String insert(String s, int i, char c) {
        if(i >= s.length()){
            return s + c;
        }
        return s.substring(0, i + 1) + c + s.substring(i + 1);
    }

    private String replace(String s, int index, char replacement) {
        return s.substring(0, index) + replacement + s.substring(index + 1);
    }

    private int parseInt(char c) {
        return Integer.parseInt(c + "");
    }

    private int calcParanthesesDelta(String expr, int start) {
        int open = 0, close = 0;
        for (int i = start; i < expr.length(); i++) {
            if (expr.charAt(i) == '[') open++;
            if (expr.charAt(i) == ']') close++;
            if (open == close) return i - start + 1;
        }
        System.out.println("Error");
        return -1;
    }


    public long initCalc(String expr) {
        return calc(new StringBuilder(expr).reverse().toString().replaceAll(" ", "")
                .replaceAll("\\(", "]").replaceAll("\\)", "["));
    }

    public long calc(String expr) {
        if (expr.length() == 1) {
            return parseInt(expr.charAt(0));
        }
        if (expr.charAt(0) == '[') {
            int delta = calcParanthesesDelta(expr, 0);
            if (delta >= expr.length()) {
                return calc(expr.substring(1, delta - 1));
            } else if (expr.charAt(delta) == '+') {
                return calc(expr.substring(1, delta - 1)) + calc(expr.substring(delta + 1));
            } else if (expr.charAt(delta) == '*') {
                return calc(expr.substring(1, delta - 1)) * calc(expr.substring(delta + 1));
            } else {
                System.out.println("PROBLEM");
                return -1;
            }
        } else if (expr.charAt(1) == '+') {
            return parseInt(expr.charAt(0)) + calc(expr.substring(2));
        } else if (expr.charAt(1) == '*') {
            return parseInt(expr.charAt(0)) * calc(expr.substring(2));
        } else {
            System.out.println("PROBLEM");
            return -1;
        }
    }
}
