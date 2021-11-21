import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day17 implements Day {
    HashMap<String, Boolean> cubes = new HashMap<>();
    int[] xRange, yRange, zRange, wRange;

    @Override
    public void prepare(String input) {

        String[] rows = input.split("\n");
        for (int x = 0; x < rows.length; x++) {
            String[] col = rows[x].split("");
            for (int y = 0; y < col.length; y++) {
                cubes.put(new Coord(x, y, 0, 0).toString(), col[y].equals("#"));
            }
        }
        xRange = new int[]{0, rows.length - 1};
        yRange = new int[]{0, rows[0].split("").length - 1};
        zRange = new int[]{0, 0};
        wRange = new int[]{0, 0};
    }

    @Override
    public String partOne() {
        for (int i = 0; i < 6; i++) {
            nextRound();
        }
        //print(cubes);
        return "" + count();
    }

    public int count() {
        int val = 0;
        for (String key : cubes.keySet()) {
            if (cubes.get(key)) {
                val++;
            }
        }
        return val;
    }

    public void nextRound() {
        //Make field one bigger into each direction
        xRange[0]--;
        xRange[1]++;
        yRange[0]--;
        yRange[1]++;
        zRange[0]--;
        zRange[1]++;
        wRange[0]--;
        wRange[1]++;

        for (int x = xRange[0]; x <= xRange[1]; x++) {
            for (int y = yRange[0]; y <= yRange[1]; y++) {
                for (int z = zRange[0]; z <= zRange[1]; z++) {
                    for (int w = wRange[0]; w <= wRange[1]; w++) {
                        Coord c = new Coord(x, y, z, w);
                        //Fill edge-fields with false
                        cubes.putIfAbsent(c.toString(), false);
                    }
                }
            }
        }


        //Copy map
        HashMap<String, Boolean> cubeCopy = new HashMap<>(cubes);

        for (String key : cubes.keySet()) {
            Coord c = new Coord(key);
            int neighbours = 0;
            for (int x = c.x - 1; x <= c.x + 1; x++) {
                for (int y = c.y - 1; y <= c.y + 1; y++) {
                    for (int z = c.z - 1; z <= c.z + 1; z++) {
                        for (int w = c.w - 1; w <= c.w + 1; w++) {
                            if (x == c.x && y == c.y && z == c.z && w == c.w) continue;
                            Boolean b = cubes.get(new Coord(x, y, z, w).toString());
                            if (b == null) continue;
                            neighbours += b ? 1 : 0;
                        }
                    }
                }
            }
            if(cubes.get(key)){
                if(neighbours != 2 && neighbours != 3){
                    cubeCopy.put(key, false);
                }
            }else{
                if(neighbours == 3){
                    cubeCopy.put(key, true);
                }
            }
        }
        cubes = cubeCopy;
    }

    @Override
    public String partTwo() {
        return "";
    }

    class Coord {
        int x, y, z, w;

        public Coord(String coord) {
            String[] coords = coord.split(",");
            this.x = Integer.parseInt(coords[0]);
            this.y = Integer.parseInt(coords[1]);
            this.z = Integer.parseInt(coords[2]);
            this.w = Integer.parseInt(coords[3]);
        }

        public Coord(int x, int y, int z, int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        @Override
        public String toString() {
            return x + "," + y + "," + z + "," + w;
        }
    }
}
