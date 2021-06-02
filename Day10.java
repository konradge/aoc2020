import Helpers.IO;

import java.util.*;

public class Day10 implements Day {
    int[] nums;
    
    int max;
    
    public void prepare(String input){
        int[] prepareNums = Arrays.stream(input.split("\n")).mapToInt(Integer::parseInt).sorted().toArray();
        nums = new int[prepareNums.length+2];
        nums[0] = 0;
        for(int i = 0; i < prepareNums.length; i++){
            nums[i+1] = prepareNums[i];
        }
        nums[nums.length-1] = nums[nums.length-2] + 3;
        max = nums[nums.length-1];
    }

    @Override
    public String partOne() {
        int ones = 0;
        int threes = 0;
        for (int i = 1; i < nums.length-1; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                ones++;
            } else if (nums[i] - nums[i - 1] == 3) {
                threes++;
            }
        }
        threes++;
        return "" + (ones * threes);
    }

    @Override
    public String partTwo() {
        return "" + connect(0);
    }
    
    HashMap<Integer, Long> memoize = new HashMap<>();

    public long connect(int startIndex) {
        if (nums[startIndex] == max) {
            return 1;
        }
        long res = 0;
        for (int endIndex = startIndex+1; endIndex <= startIndex + 3; endIndex++) {
            if(endIndex >= nums.length-1){
                continue;
            }
            if(nums[startIndex] + 3 >= nums[endIndex]){
                if(memoize.get(endIndex) == null){
                    memoize.put(endIndex, connect(endIndex));
                }
                res += memoize.get(endIndex);
            }
        }
        if(res == 0){
            res = 1;
        }
        return res;
    }
}
