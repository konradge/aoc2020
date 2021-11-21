import java.util.*;

public class Day14 implements Day {
    String[] cmds;

    @Override
    public void prepare(String input) {
        cmds = input.split("\n");
    }


    @Override
    public String partOne() {
        MemoryP1 mem = new MemoryP1();
        for (String cmd : cmds) {
            mem.executeNextCommand(cmd);
        }
        return "" + mem.getSum();
    }

    public static String fill(String s, int length) {
        StringBuilder str = new StringBuilder();
        while (str.length() + s.length() < length) {
            str.append("0");
        }

        return str + s;
    }


    HashMap<Long, Long> mem = new HashMap<>();
    String[] masks;

    @Override
    public String partTwo() {
        for (String cmd : cmds) {
            //System.out.println("Executing " + cmd);
            if (cmd.startsWith("mask")) {
                String raw_mask = cmd.split(" = ")[1];
                //parse Masks with all permutations
                int n = countChar(raw_mask, 'X');
                String[] x_replacements = getBinaryNumbers(n);
                
                masks = new String[(int)Math.pow(2, n)];
                for(int i = 0; i < x_replacements.length; i++){
                    StringBuilder curr_mask = new StringBuilder(raw_mask);
                    String replacement = x_replacements[i].replaceAll("0", "2");
                    int b = 0;
                    for(int j = 0; j < curr_mask.length(); j++){
                        if(curr_mask.charAt(j) == 'X'){
                            curr_mask.setCharAt(j, replacement.charAt(b++));
                        }
                    }
                    masks[i] = curr_mask.toString();
                }
                
                //Sehr Wahrscheinlich OK bis hier
            } else {
                //Parse number
                String bin_num = fill(Integer.toBinaryString(Integer.parseInt(cmd.split("(\\[|\\])")[1])), 36);
                Long val = Long.parseLong(cmd.split(" = ")[1]);
                for(String mask: masks){
                    //Apply each mask on it
                    String bin_new = applyMask(bin_num, mask);
                    //Set register-entrys to true
                    mem.put(Long.parseLong(bin_new, 2), val);
                }
            }
        }

        //Sum all mem-Entries with true
        long sum = 0;
        for(Long key: mem.keySet()){
                sum+= mem.get(key);
        }
        return "" + sum;
    }
    
    public String applyMask(String num, String mask){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < mask.length(); i++){
            if(mask.charAt(i) == '2'){
                res.append("0");
            }else if(mask.charAt(i) == '1'){
                res.append("1");
            }else{
                res.append(num.charAt(i));
            }
        }
        return res.toString();
    }

    public String[] getBinaryNumbers(int n) {
        String highestBin = Integer.toBinaryString((int)Math.pow(2, n));
        String[] res = new String[(int)Math.pow(2, n)];
        for (int i = 0; i < Math.pow(2, n); i++) {
            res[i] = fill(Integer.toBinaryString(i), n);
        }
        return res;
    }

    public int countChar(String str, char c) {
        int count = 0;
        for (String s : str.split("")) {
            if (s.equals(c + "")) {
                count++;
            }
        }
        return count;
    }
}

class MemoryP1 {
    int[] mask = new int[36];
    HashMap<Integer, Long> mem = new HashMap<>();

    public long getSum() {
        long sum = 0;
        for (Integer k : mem.keySet()) {
            sum += mem.get(k);
        }
        return sum;
    }


    public void executeNextCommand(String cmd) {
        String prefix = cmd.split("( = |\\[)")[0];
        if (prefix.equals("mask")) {
            this.parseMask(cmd);
        } else {
            ParseCommand pcmd = ParseCommand.parse(cmd);
            StringBuilder memEntry = new StringBuilder();
            for (int i = 0; i < pcmd.value.length(); i++) {
                if (mask[i] == -1) {
                    memEntry.append(pcmd.value.charAt(i));
                } else {
                    memEntry.append(mask[i]);
                }
            }
            mem.put(pcmd.register, Long.parseLong(memEntry.toString(), 2));
        }
    }

    public void parseMask(String cmd) {
        String mask = cmd.split(" = ")[1];
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == 'X') {
                this.mask[i] = -1;
            } else if (mask.charAt(i) == '0') {
                this.mask[i] = 0;
            } else {
                this.mask[i] = 1;
            }
        }
    }
}

class ParseCommand {
    int register;
    String value;

    private ParseCommand(int register, String value) {
        this.register = register;
        this.value = value;
    }

    public static ParseCommand parse(String cmd) {
        String[] splitCmd = cmd.split("(\\[|\\] = )");
        int register = Integer.parseInt(splitCmd[1]);
        String value = Day14.fill(Long.toBinaryString(Long.parseLong(splitCmd[2])), 36);

        return new ParseCommand(register, value);
    }
}
