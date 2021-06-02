import java.util.ArrayList;
import java.util.Arrays;

public class Day16 implements Day {
    Rule[] rules;
    Ticket[] tickets;
    Ticket myTicket;

    @Override
    public void prepare(String input) {
        String[] parts = input.split("\n\n");
        String[] strRules = parts[0].split("\n");
        rules = new Rule[strRules.length];
        for (int i = 0; i < strRules.length; i++) {
            rules[i] = new Rule(strRules[i]);
        }
        String[] strTickets = parts[2].replace("nearby tickets:\n", "").split("\n");
        tickets = new Ticket[strTickets.length];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket(strTickets[i]);
        }
        myTicket = new Ticket(parts[1].split("\n")[1]);
    }

    @Override
    public String partOne() {
        ArrayList<Integer> errors = new ArrayList<>();
        for (Ticket t : tickets) {
            errors.addAll(t.getInvalid(rules));
        }
        return "" + errors.stream().reduce((acc, val) -> acc + val);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String partTwo() {   
        //Array, where each field is the possible Rule to apply at the corresponding index in each ticket
        ArrayList<Rule>[] possible = (ArrayList<Rule>[]) new ArrayList[rules.length];
        for (int i = 0; i < possible.length; i++) {
            possible[i] = new ArrayList<>();
            for (Rule r : rules) {
                possible[i].add(r);
            }
        }
        
        //Check each Ticket (including MyTicket, if any rules can be eliminated
        checkTicket(myTicket, possible);
        for (Ticket t : tickets) checkTicket(t, possible);
        
        
        ArrayList<Integer> checked = new ArrayList<>();
        for (int i = 0; i < possible.length; i++) {
            if (checked.contains(i)) {
                //Rule has already been checked (There is just one possible rule)
                continue;
            }
            if (possible[i].size() == 1) {
                //There is just one rule, which can be applied at this index at any ticket, so it must be the correct rule
                //and this rule has to be removed at any other index
                for (int j = 0; j < possible.length; j++) {
                    if (i != j) {
                        //Remove rule at any other position
                        possible[j].remove(possible[i].get(0));
                    }
                }
                checked.add(i);
                i = 0;
            }
        }
        long val = 1;
        for(int i = 0; i < possible.length; i++){
            Rule rule = possible[i].get(0);
            if(!rule.name.startsWith("departure")) continue;
            val *= myTicket.fields[i];
        }
        return "" + val;
    }

    void checkTicket(Ticket t, ArrayList<Rule>[] possible) {
        if (t.isValid(rules)) {
            //Invalid tickets are ignored
            for (int i = 0; i < t.fields.length; i++) {
                //Go through each number in the ticket and check, which rules can be applied
                final int num = t.fields[i];
                possible[i].removeIf(r -> !r.isValid(num));
            }
        }else{
            System.out.println(t + " invalid!");
        }
    }
}

class Ticket {
    int[] fields;

    public Ticket(String numbers) {
        this(numbers.split(","));
    }

    public Ticket(String[] numbers) {
        fields = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
    }

    public boolean isValid(Rule[] rules) {
        for (int field : fields) {
            boolean isValid = false;
            for (Rule rule : rules) {
                if (rule.isValid(field)) {
                    isValid = true;
                }
            }
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> getInvalid(Rule[] rules) {
        ArrayList<Integer> res = new ArrayList<>();
        field:
        for (int field : fields) {
            for (Rule rule : rules) {
                if (rule.isValid(field)) {
                    continue field;
                }
            }
            res.add(field);
        }
        return res;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "fields=" + Arrays.toString(fields) +
                '}';
    }
}

class Rule {
    String name;
    int[] range = new int[4]; //[Lower-Min, Lower-Max, Upper-Min, Upper-Max

    public Rule(String rule) {
        name = rule.split(":")[0];
        parseRange(rule.split(": ")[1].split(" or "));
    }

    public void parseRange(String[] ranges) {
        for (int i = 0; i < ranges.length * 2; i += 2) {
            String[] nums = ranges[i / 2].split("-");
            range[i] = Integer.parseInt(nums[0]);
            range[i + 1] = Integer.parseInt(nums[1]);
        }
    }

    public boolean isValid(int number) {
        return !(number < range[0] || number > range[3] || (number > range[1] && number < range[2]));
    }

    @Override
    public String toString() {
        return "<" + name + ": " + range[0] + "-" + range[1] + " or " + range[2] + "-" + range[3] + ">";
    }
}
