import java.util.Arrays;

public class Day9 implements Day{
    long[] numbers;
    
    public void prepare(String input){
        String[] lines = input.replaceAll("\r", "").split("\n");
        numbers = new long[lines.length];
        for(int i = 0; i < lines.length; i++){
            numbers[i] = Long.parseLong(lines[i].trim());
        }
    }
    @Override
    public String partOne() {
        list: for(int i = 25; i < numbers.length; i++){
            for(int j = i-25; j < i; j++){
                for(int k = i-25; k < i; k++){
                    if(k != j){
                        if(numbers[j] + numbers[k] == numbers[i]){
                            continue list;
                        }
                    }
                }
            }
            return "" + numbers[i];
        }
        return "nothing found";
    }

    @Override
    public String partTwo() {
        for(int i = 0; i < numbers.length; i++){
            for(int j = i; j < numbers.length; j++){
                if(sum(i, j) == 1309761972){
                    long[] range = Arrays.stream(Arrays.copyOfRange(numbers, i, j+1)).sorted().toArray();
                    return range[0] + range[range.length-1] + "";
                }
            }
        }
        return "";
    }
    
    public long sum(int startIndex, int endIndex){
        long res = 0;
        for(int i = startIndex; i <= endIndex; i++){
            res += numbers[i];
        }
        return res;
    }
}
