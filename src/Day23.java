import java.util.Arrays;
import java.util.HashMap;

public class Day23 implements Day {
    HashMap<String, Boolean> tiles;
    String input;

    @Override
    public void prepare(String input) {
        this.input = input;
    }

    @Override
    public String partOne() {
        tiles = new HashMap<>();
        flipFloor();
        return "" + countBlack();
    }

    @Override
    public String partTwo() {
        tiles = new HashMap<>();

        flipFloor();
        for (int i = 0; i < 100; i++) {

            //print();
            die();
            //print();
        }
        return "" + countBlack();
    }

    void print(){
        for(String key:tiles.keySet()){
            if(tiles.get(key))
            System.out.print(key + ", ");
        }
        System.out.println("------");
    }

    public void flipFloor() {
        String dir = "";
        for (String line : this.input.split("\n")) {
            int x = 0, y = 0;
            for (String s : line.split("")) {
                dir += s;
                if (s.equals("e") || s.equals("w")) {
                    switch (dir) {
                        case "e":
                            x += 2;
                            break;
                        case "w":
                            x -= 2;
                            break;
                        case "ne":
                            y += 2;
                            x += 1;
                            break;
                        case "se":
                            y -= 2;
                            x += 1;
                            break;
                        case "nw":
                            y += 2;
                            x -= 1;
                            break;
                        case "sw":
                            y -= 2;
                            x -= 1;
                            break;
                        default:
                            throw new RuntimeException("Invalid input!");
                    }
                    dir = "";
                }
            }
            String key = x + "," + y;
            boolean currentState = tiles.getOrDefault(key, false);
            tiles.put(x + "," + y, !currentState);
        }
    }

    public void die() {

        HashMap<String, Boolean> nextTiles = new HashMap(tiles);
        // Add neighbours of all tiles that are currently black
        for (String tile : tiles.keySet()) {
            if (tiles.get(tile)) {
                Point p = new Point(tile);
                for (String neighbour : p.getNeighbours()) {
                    if (!tiles.containsKey(neighbour)) nextTiles.put(neighbour, false);
                }
            }
        }
        tiles = nextTiles;
        nextTiles = new HashMap(tiles);
        for (String tile : tiles.keySet()) {
            Point p = new Point(tile);
            int neighbours = 0;
            for (String neighbour : p.getNeighbours()) {
                if (tiles.getOrDefault(neighbour, false)) {
                    neighbours++;
                }
            }
            if (tiles.get(tile)) {
                if (neighbours == 0 || neighbours > 2) {
                    nextTiles.put(tile, false);
                }
            } else {
                if (neighbours == 2) {
                    nextTiles.put(tile, true);
                }
            }
        }

        tiles = nextTiles;
    }

    public int countBlack() {
        int blackCount = 0;
        for (boolean state : tiles.values()) {
            if (state) {
                blackCount++;
            }
        }
        return blackCount;
    }
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String point) {
        String[] coords = point.split(",");
        this.x = Integer.parseInt(coords[0]);
        this.y = Integer.parseInt(coords[1]);
    }

    public String[] getNeighbours() {
        return new String[]{
                new Point(x + 2, y).toString(),
                new Point(x - 2, y).toString(),
                new Point(x + 1, y + 2).toString(),
                new Point(x - 1, y + 2).toString(),
                new Point(x + 1, y - 2).toString(),
                new Point(x - 1, y - 2).toString()};
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
