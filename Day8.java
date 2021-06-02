import java.util.Arrays;

public class Day8 implements Day{
    String[] lines;
    public void prepare(String input){
        lines = input.split("\n");
    }
    @Override
    public String partOne() {
        outter: for(int j = 0; j < lines.length; j++) {
            int acc = 0;
            String[] linesCopy = Arrays.copyOf(lines, lines.length);
            for (int i = 0; i < linesCopy.length; ) {
                String[] cmd = linesCopy[i].split(" ");
                if(i == j){
                    if(cmd[0].equals("nop")){
                        cmd[0] = "jmp";
                    }else if(cmd[0].equals("jmp")){
                        cmd[0] = "nop";
                    }
                }
                linesCopy[i] = "vstd";
                if (cmd[0].equals("vstd")) {
                    continue outter;
                }
                if (cmd[0].equals("acc")) {
                    acc += Integer.parseInt(cmd[1]);
                    i++;
                } else if (cmd[0].equals("jmp")) {
                    i += Integer.parseInt(cmd[1]);
                } else {
                    i++;
                }
            }
            return "" + acc;
        }
        return "end";
    }

    @Override
    public String partTwo() {
        return "";
    }
}
