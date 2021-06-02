import java.util.Arrays;
import java.util.HashMap;

public class Day19 implements Day {
    HashMap<Integer, Rule> rules = new HashMap<>();
    String[] tests;

    @Override
    public void prepare(String input) {
        String[] upperInput = input.split("\\n\\n")[0].split("\\n");
        for (String rule : upperInput) {
            rules.put(Integer.parseInt(rule.split(":")[0]), new Rule(rule.split(": ")[1]));
        }

        tests = input.split("\\n\\n")[1].split("\\n");
    }

    @Override
    public String partOne() {
        return "" + countMatches();
    }

    @Override
    public String partTwo() {
        rules.put(8, new Rule("42 | 42 8", 8));
        rules.put(11, new Rule("42 31 | 42 11 31", 11));
        return "" + countMatches();
    }

    int countMatches() {
        String match = rules.get(0).getMatch();
        int matches = 0;
        for (String test : tests) {
            if (test.matches("^" + match + "$")) {
                matches++;
            }
        }
        return matches;
    }

    public class Rule {
        int[][] options;
        char character;
        int ruleNumber = -1;
        
        public Rule(String descr, int rule){
            this.ruleNumber = rule;
        }

        public Rule(String descr) {
            if (descr.matches("(\"[ab]\")")) {
                character = descr.charAt(1);
            } else {
                String[] opts = descr.split(" \\| ");
                options = new int[opts.length][];
                for (int i = 0; i < opts.length; i++) {
                    String[] ruleOrder = opts[i].split(" ");
                    options[i] = new int[ruleOrder.length];
                    for (int j = 0; j < ruleOrder.length; j++) {
                        int rule = Integer.parseInt(ruleOrder[j]);
                        options[i][j] = rule;
                    }
                }
            }
        }
        

        private String getMatch() {
            if(ruleNumber == 8){
                String match42 = rules.get(42).getMatch();
               StringBuilder res = new StringBuilder("(");
               for(int i = 5; i > 0; i--){
                   for(int j = 0; j <=i; j++){
                       res.append(match42);
                   }
                   res.append("|");
               }
               return res.append(match42).append(")").toString();
            }else if(ruleNumber == 11){
                String match42 = rules.get(42).getMatch();
                String match31 = rules.get(31).getMatch();
                StringBuilder res = new StringBuilder("(");
                for(int i = 5; i > 0; i--){
                    for(int j = 0; j <=i; j++){
                        res.append(match42);
                    }
                    for(int j = 0; j <=i; j++){
                        res.append(match31);
                    }
                    res.append("|");
                }
                return res.append(match42).append(match31).append(")").toString();
            }else if (options == null) {
                return character + "";
            }else {
                String m = "(";
                for (int[] option : options) {
                    for (int rule : option) {
                        m += rules.get(rule).getMatch();
                    }
                    m += "|";
                }
                return m.substring(0, m.length() - 1) + ")";
            }
        }
    }

}
