import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day20 implements Day {
    ArrayList<Tile> allTiles = new ArrayList<>();

    int n;

    @Override
    public void prepare(String input) {
        for (String s : input.split("\\n\\n")) {
            allTiles.add(new Tile(s));
        }
        n = (int) Math.sqrt(allTiles.size() / 4);
        System.out.println(allTiles);
    }

    @Override
    public String partOne() {
        long res = 1;
        for (Tile tile : allTiles) {
            ArrayList<Tile> other = new ArrayList<>(allTiles);
            other.remove(tile);
            int adjCount = 0;
            if (matchesAny(tile.north, other)) adjCount++;
            if (matchesAny(tile.west, other)) adjCount++;
            if (matchesAny(tile.south, other)) adjCount++;
            if (matchesAny(tile.east, other)) adjCount++;
            if(adjCount == 2){
                res *= tile.id;
            }
        }
        return res + "";
    }

    public boolean matchesAny(String tilesDir, ArrayList<Tile> otherTiles) {
        for (Tile t : otherTiles) {
            if (isMatchingSide(tilesDir, t.south) || isMatchingSide(tilesDir, t.east) ||
                    isMatchingSide(tilesDir, t.west) || isMatchingSide(tilesDir, t.north)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMatchingSide(String s1, String s2) {
        return s1.equals(s2) || new StringBuilder(s1).reverse().toString().equals(s2);
    }

    @Override
    public String partTwo() {
        return null;
    }

    public class Tile {
        String[][] content;
        int id;
        String north, south, west, east;

        //Rotation im Uhrzeigersinn
        public Tile(String descr, int rotation) {
            String tile = descr.split(":\\n")[1];
            int size = tile.split("\\n").length;
            content = new String[size][size];
            for (int i = 0; i < size; i++) {
                String row = tile.split("\\n")[i];
                for (int j = 0; j < size; j++) {
                    String s = row.charAt(j) + "";
                    if (rotation == 0) {
                        content[i][j] = s;
                    } else if (rotation == 90) {
                        content[j][size - 1 - i] = s;
                    } else if (rotation == 180) {
                        content[size - 1 - i][size - 1 - j] = s;
                    } else if (rotation == 270) {
                        content[size - 1 - j][i] = s;
                    }
                }
            }
            id = Integer.parseInt(descr.split(":")[0].split(" ")[1]);
            north = String.join("", content[0]);
            south = String.join("", content[content.length - 1]);
            west = east = "";
            for (int i = 0; i < size; i++) {
                west += content[i][0];
                east += content[i][size - 1];
            }
        }

        public Tile(String descr) {
            this(descr, 0);
        }

        public boolean matches(Tile tile, Directions dir) {
            if (this.id == tile.id) {
                return false;
            } else if (dir == Directions.NORTH) {
                return this.north.equals(tile.south);
            } else if (dir == Directions.SOUTH) {
                return this.south.equals(tile.north);
            } else if (dir == Directions.WEST) {
                return this.west.equals(tile.east);
            } else {
                return this.east.equals(tile.west);
            }
        }

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            for (String[] row : content) {
                for (String s : row) {
                    res.append(s);
                }
                res.append(System.lineSeparator());
            }
            return res.toString();
        }
    }

    enum Directions {
        NORTH, SOUTH, WEST, EAST;
    }
}
