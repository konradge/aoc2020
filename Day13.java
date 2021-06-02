import Helpers.*;

import java.util.Arrays;

public class Day13 implements Day {
    int[] ids;
    int time, busCount = 0;

    @Override
    public void prepare(String input) {
        time = Integer.parseInt(input.split("\n")[0]);
        String[] busses = input.split("\n")[1].split(",");
        ids = Arrays.stream(busses).mapToInt(x -> {
            if (x.equals("x")) {
                return -1;
            } else {
                busCount++;
                return Integer.parseInt(x);
            }
        }).toArray();
    }

    @Override
    public String partOne() {
        int[] diffs = new int[busCount];
        int index = 0;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == -1) continue;
            int n = 0;
            while (n * ids[i] < time) {
                n++;
            }
            diffs[index++] = (n * ids[i] - time);
        }
        int min = Integer.MAX_VALUE, id = 0;
        for (int i = 0; i < diffs.length; i++) {
            if (min > diffs[i]) {
                min = diffs[i];
                id = ids[i];
            }
        }

        return id * min + "";
    }

    @Override
    public String partTwo() {
        long time = 0;
        
        //Time after the time, where the bus should come
        int[] dtime = new int[9];
        
        //Times, the bus arrives
        int[] modulos = new int[9];
        
        //Fill dtime and modulos from input
        int index = 0;
        for(int i = 0; i < ids.length; i++){
            if(ids[i] != -1) {
                modulos[index] = ids[i];
                dtime[index] = i;
                index++;
            }
        }
        
        //Product of all modulos
        long M = 1;
        for (int m : modulos) {
            M *= m;
        }

        //Calculate with the Chinese Remainder Theorem
        for (int i = 0; i < modulos.length; i++) {
            //Get greatest common divider in form 1 = my * modulos[i] + lam * (M / modulos[i])
            Solution res = ExtendedEuclidianAlghorithm.getGCD(M / modulos[i], modulos[i]);
            
            //Add this to the time, according to the CRT
            time += res.lam * (M / modulos[i]) * dtime[i];
        }
        return "" + Math.abs(time % M);
    }
}
