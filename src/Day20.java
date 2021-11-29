import java.util.ArrayList;

public class Day20 implements Day {
    ArrayList<Tile> allTiles = new ArrayList<>(), edges = new ArrayList<>(), corners = new ArrayList<>();
    int puzzleSize, tileSize;
    Tile[][] solution = null;

    @Override
    public void prepare(String input) {
        for (String s : input.split("\\n\\n")) {
            allTiles.add(new Tile(s));
        }
        puzzleSize = (int) Math.sqrt(allTiles.size());
        // Find edges
        for (Tile tile : allTiles) {
            if (tile.countUnmatchableSites() == 1) {
                edges.add(tile);
            } else if (tile.countUnmatchableSites() == 2) {
                corners.add(tile);
            }
        }

        solvePuzzle(0, 0, allTiles, new Tile[puzzleSize][puzzleSize]);
    }

    @Override
    public String partOne() {
        long res = (long) solution[0][0].id * solution[puzzleSize - 1][0].id * solution[puzzleSize - 1][puzzleSize - 1].id * solution[0][puzzleSize - 1].id;
        return "" + res;
    }

    @Override
    public String partTwo() {

        StringBuilder builder = new StringBuilder("Tile 0:\n");
        for (int i = 0; i < puzzleSize; i++) {
            for (int k = 1; k < tileSize - 1; k++) {
                for (int j = 0; j < puzzleSize; j++) {
                    for (int l = 1; l < tileSize - 1; l++) {
                        builder.append(solution[j][i].content[k][l]);
                    }
                }
                builder.append("\n");
            }
        }
        Tile solution = new Tile(builder.toString());
        return "" + solution.findMonster();
    }

    public void solvePuzzle(int i, int j, ArrayList<Tile> remainingTiles, Tile[][] puzzle) {
        if (solution != null) return;

        boolean isCorner = ((i == 0 || i == puzzleSize - 1) && (j == 0 || j == puzzleSize - 1));
        boolean isEdge = (i == 0 || j == 0 || i == puzzleSize - 1 || j == puzzleSize - 1) && !isCorner;

        if (i == puzzleSize) {
            i = 0;
            j++;
            if (j == puzzleSize) {
                this.solution = puzzle;
                return;
            }
        }
        for (Tile tile : remainingTiles) {
            if (isCorner && corners.stream().noneMatch(c -> c == tile)) continue;
            if (isEdge && edges.stream().noneMatch(c -> c.id == tile.id)) continue;
            for (Tile t : tile.getAllConfigurations()) {
                if (matches(t, i, j, puzzle)) {
                    Tile[][] clone = clonePuzzle(puzzle);
                    clone[i][j] = t;
                    solvePuzzle(i + 1, j, new ArrayList<>(remainingTiles.stream().filter(remTile -> remTile.id != t.id).toList()), clone);
                }
            }
        }
    }

    public ArrayList<Tile> intersectLists(ArrayList<Tile> l1, ArrayList<Tile> l2) {
        return new ArrayList<Tile>(l1.stream().filter(tile -> l2.stream().filter(t -> t.id == tile.id).toList().size() != 0).toList());
    }

    public ArrayList<Tile> unionList(ArrayList<Tile> l1, ArrayList<Tile> l2) {
        ArrayList<Tile> res = new ArrayList<>(l1);
        res.addAll(l2);
        return res;
    }

    public Tile[][] clonePuzzle(Tile[][] puzzle) {
        Tile[][] res = new Tile[puzzle.length][puzzle.length];
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                if (puzzle[i][j] != null)
                    res[i][j] = puzzle[i][j].copy();
            }
        }
        return res;
    }

    public boolean matches(Tile tileToMatch, int i, int j, Tile[][] puzzle) {
        boolean res = true;
        if (i != 0 && puzzle[i - 1][j] != null) {
            // puzzle ttm
            res &= tileToMatch.getEdge(Directions.WEST).equals(puzzle[i - 1][j].getEdge(Directions.EAST));
        }
        if (i != puzzleSize - 1 && puzzle[i + 1][j] != null) {
            // ttm puzzle
            res &= tileToMatch.getEdge(Directions.EAST).equals(puzzle[i + 1][j].getEdge(Directions.WEST));
        }
        if (j != 0 && puzzle[i][j - 1] != null) {
            // puzzle
            // ttm
            res &= tileToMatch.getEdge(Directions.NORTH).equals(puzzle[i][j - 1].getEdge(Directions.SOUTH));
        }
        if (j != puzzleSize - 1 && puzzle[i][j + 1] != null) {
            // ttm
            // puzzle
            res &= tileToMatch.getEdge(Directions.SOUTH).equals(puzzle[i][j + 1].getEdge(Directions.NORTH));
        }
        return res;
    }

    public class Tile {
        String[][] content;
        int id, size;
        String tile;

        //Rotation im Uhrzeigersinn
        public Tile(String descr) {
            this.tile = descr.split(":\\n")[1];
            this.size = tile.split("\\n").length;
            tileSize = this.size;
            content = new String[size][size];
            //Fill tile
            for (int i = 0; i < size; i++) {
                String line = this.tile.split("\\n")[i];
                System.arraycopy(line.split(""), 0, content[i], 0, size);
            }
            id = Integer.parseInt(descr.split(":")[0].split(" ")[1]);
        }

        public Tile() {
            // Empty constructor
        }

        int[] x = new int[]{1, 2, 5, 6, 7, 8, 11, 12, 13, 14, 17, 18, 19, 19, 20};
        int[] y = new int[]{2, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 2, 1, 2, 2};

        public int findMonster() {
            int monsters = 0, monsterParts = 0;
            for (Tile config : this.getAllConfigurations()) {
                monsters = 0;
                for (int i = 0; i < config.content.length - 3; i++) {
                    for (int j = 0; j < config.content[i].length - 20; j++) {
                        monsterParts = 0;
                        for (int k = 0; k < x.length; k++) {
                            if (config.content[j + x[k]][i + y[k]].equals("#")) {
                                config.content[j + x[k]][i + y[k]] = "#";
                                monsterParts++;
                            }
                        }
                        if (monsterParts == 15) {
                            monsters++;
                        }
                    }
                }

                if (monsters != 0) {
                    int res = 0;
                    for (int i = 0; i < config.content.length; i++) {
                        for (int j = 0; j < config.content[i].length; j++) {
                            if (config.content[i][j].equals("#")) res++;
                        }
                    }
                    return res - monsters * 15;
                }
            }
            throw new RuntimeException("Should not be here!");
        }

        public Tile copy() {
            Tile copy = new Tile();
            copy.content = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    copy.content[i][j] = content[i][j];
                }
            }
            copy.id = id;
            copy.size = size;
            copy.tile = tile;
            return copy;
        }

        public int countUnmatchableSites() {
            int count = 0;
            for (Directions dir : Directions.getDirections()) {
                if (hasNoMatch(dir)) count++;
            }
            return count;
        }

        public boolean hasNoMatch(Directions dir) {
            for (Tile t : allTiles.stream().filter(tile -> tile.id != this.id).toList()) {
                if (matches(this, t, dir)) return false;
            }
            return true;
        }

        public boolean matches(Tile t1, Tile t2, Directions dir) {
            for (Directions otherDir : Directions.getDirections()) {
                if (t1.getEdge(dir).equals(t2.getEdge(otherDir))) return true;
                if (t1.getEdge(dir).equals(new StringBuilder(t2.getEdge(otherDir)).reverse().toString())) return true;
            }
            return false;
        }

        /**
         * @return All possible configurations from this tile after flipping and rotating
         */
        public ArrayList<Tile> getAllConfigurations() {
            ArrayList<Tile> res = new ArrayList<>();
            res.addAll(this.flipLR().getAllRotations());
            res.addAll(this.flipTB().getAllRotations());
            res.addAll(this.flipLR().getAllRotations());
            res.addAll(this.flipTB().getAllRotations());
            assert res.size() == 16;
            return res;
        }

        /**
         * @return All possible rotations (0°, 90°, 180°, 270°)
         */
        public ArrayList<Tile> getAllRotations() {
            ArrayList<Tile> res = new ArrayList<>();
            res.add(this.copy());
            for (int i = 90; i < 360; i += 90) {
                res.add(this.copy().rotate(i));
            }
            assert res.size() == 4;
            return res;
        }

        public Tile flipLR() {
            String[][] newContent = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    newContent[i][j] = content[i][size - j - 1];
                }
            }
            this.content = newContent;
            return this;
        }

        public Tile flipTB() {
            String[][] newContent = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    newContent[i][j] = content[size - i - 1][j];
                }
            }
            this.content = newContent;
            return this;
        }

        public Tile rotate(int degrees) {
            degrees = (360 + degrees) % 360;
            for (int deg = 0; deg < degrees; deg += 90) {
                String[][] newContent = new String[size][size];
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        // Rows -> Columns
                        newContent[i][j] = content[size - j - 1][i];
                    }
                }
                this.content = newContent;
            }
            return this;
        }

        public String getEdge(Directions dir) {
            if (dir == Directions.NORTH) {
                return String.join("", content[0]);
            } else if (dir == Directions.SOUTH) {
                return String.join("", content[size - 1]);
            } else if (dir == Directions.EAST) {
                StringBuilder res = new StringBuilder();
                for (String[] line : content) {
                    res.append(line[size - 1]);
                }
                return res.toString();
            } else if (dir == Directions.WEST) {
                StringBuilder res = new StringBuilder();
                for (String[] line : content) {
                    res.append(line[0]);
                }
                return res.toString();
            }
            throw new RuntimeException("Invalid argument " + dir + " given");
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

        static ArrayList<Directions> getDirections() {
            ArrayList<Directions> res = new ArrayList<>();
            res.add(NORTH);
            res.add(SOUTH);
            res.add(WEST);
            res.add(EAST);
            return res;
        }
    }
}
