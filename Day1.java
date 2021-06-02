public class Day1 implements Day {
    String[] rows;
    
    public void prepare(String input){
        rows = input.split("\n");
    }

    public String partOne() {
        for (String row : rows) {
            int i = Integer.parseInt(row);
            for (String r : rows) {
                int j = Integer.parseInt(r);
                if (r != row && i + j == 2020) {
                    return "" + i * j;
                }
            }
        }
        return "" + 0;
    }


    public String partTwo() {
        for (String a : rows) {
            int i = Integer.parseInt(a);
            for (String b : rows) {
                int j = Integer.parseInt(b);
                for (String c : rows) {
                    int k = Integer.parseInt(c);
                    if (a != b && b != c && (i + j + k) == 2020) {
                        return "" + (i * j * k);
                    }
                }
            }
        }
        return "" + 0;
    }
}
