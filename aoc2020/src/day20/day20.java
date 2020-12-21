package day20;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day20 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day20/day20.txt"));
        final List<Tile> tiles = new ArrayList<>();
        long id = 0;
        List<char[]> grid = new ArrayList<>();
        for (String line : input) {
            if (line.isEmpty()) {
                tiles.add(new Tile(id, grid));
                continue;
            }

            if (line.startsWith("Tile")) {
                id = Long.parseLong(line.substring(line.indexOf(' ') + 1, line.length() - 1));
                grid = new ArrayList<>();
                continue;
            }

            grid.add(line.toCharArray());
        }

        tiles.add(new Tile(id, grid));

        part1(tiles);
        part2(tiles);
    }

    private static void part1(List<Tile> tiles) {
        Tile.buildGraph(tiles);
        System.out.println(tiles.stream().filter(a -> a.isCorner()).map(a -> a.id).reduce((a, b) -> a *= b).get());
    }

    private static void part2(List<Tile> tiles) {
        final boolean[][] monster = new boolean[][] {
            { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false },
            { true, false, false, false, false, true, true, false, false, false, false, true, true, false, false, false, false, true, true, true },
            { false, true, false, false, true, false, false, true, false, false, true, false, false, true, false, false, true, false, false, false }
        };

        Tile.buildGraph(tiles);

        List<List<Boolean>> image = Tile.getImage(tiles);
        final long totalRoughness = image.stream().map(a -> a.stream().map(b -> b ? 1 : 0).reduce((c, d) -> c + d).get()).reduce((a, b) -> a + b).get();

        for (int f = 0; f < 2; f++) {
            for (int r = 0; r < 4; r++) {
                long count = 0;
                for (int i = 0; i < image.size() - monster.length; i++) {
                    for (int j = 0; j < image.get(i).size() - monster[0].length; j++) {
                        boolean found = true;
                        for (int k = 0; k < monster.length; k++) {
                            for (int l = 0; l < monster[k].length; l++) {
                                if (monster[k][l] && !image.get(i + k).get(j + l)) {
                                    found = false;
                                }
                            }
                        }

                        count += found ? 1 : 0;
                    }
                }

                if (count > 0) {
                    System.out.println(totalRoughness - count * 15);
                    return;
                }

                image = rotate(image);
            }

            Collections.reverse(image);
        }
    }

    private static List<List<Boolean>> rotate(List<List<Boolean>> image) {
        final List<List<Boolean>> newimage = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            newimage.add(new ArrayList<>());
            for (int j = 0; j < image.get(i).size(); j++) {
                newimage.get(i).add(image.get(image.size() - j - 1).get(i));
            }
        }

        return newimage;
    }

    private static class Tile {
        private static final int SIZE = 10;

        private boolean[][] grid;

        public final long id;

        public Tile up;
        public Tile right;
        public Tile down;
        public Tile left;

        public Tile(long id, List<char[]> grid) {
            this.id = id;
            this.grid = new boolean[SIZE][];
            for (int i = 0; i < SIZE; i++) {
                this.grid[i] = new boolean[SIZE];
                for (int j = 0; j < SIZE; j++) {
                    this.grid[i][j] = grid.get(i)[j] == '#';
                }
            }
        }

        public boolean isCorner() {
            int c = 0;
            c += this.up == null ? 1 : 0;
            c += this.right == null ? 1 : 0;
            c += this.down == null ? 1 : 0;
            c += this.left == null ? 1 : 0;

            return c == 2;
        }

        public static void buildGraph(List<Tile> tiles) {
            final Tile seed = tiles.get(0);

            final Set<Tile> loose = new HashSet<>(tiles);
            loose.remove(seed);
            final Deque<Tile> queue = new ArrayDeque<>();
            queue.add(seed);
            while (!queue.isEmpty()) {
                final Tile current = queue.pop();
                for (Tile t : loose.toArray(new Tile[0])) {
                    for (int i = 0; i < 2; i++) {
                        t.flip();

                        for (int j = 0; j < 4; j++) {
                            if (current.findMatch(t)) {
                                loose.remove(t);
                                queue.add(t);

                                i = 2;
                                break;
                            }

                            t.rotate();
                        }
                    }
                }
            }
        }

        public static List<List<Boolean>> getImage(List<Tile> tiles) {
            final List<List<Boolean>> out = new ArrayList<>();

            Tile current = tiles.stream().filter(a -> a.up == null && a.left == null).findFirst().get();
            Tile first;
            Tile nextrow;
            while (current != null) {
                first = current;
                nextrow = current.down;
                while (current != null) {
                    for (int i = 1; i <= SIZE - 2; i++) {
                        current = first;
                        final List<Boolean> line = new ArrayList<>();
                        Tile nextcol;
                        while (current != null) {
                            nextcol = current.right;
                            for (int j = 1; j <= SIZE - 2; j++) {
                                line.add(current.grid[i][j]);
                            }

                            current = nextcol;
                        }
                    
                        out.add(line);
                    }
                }

                current = nextrow;
            }

            return out;
        }

        private boolean findMatch(Tile tile) {
            if (this.up == null && matchesUp(tile)) {
                this.up = tile;
                tile.down = this;

                if (this.left != null && this.left.up != null) {
                    tile.left = this.left.up;
                    this.left.up.right = tile;
                }

                if (this.right != null && this.right.up != null) {
                    tile.right = this.right.up;
                    this.right.up.left = tile;
                }

                if (tile.left != null && tile.left.down != null) {
                    this.left = tile.left.down;
                    tile.left.down.right = this;
                }

                if (tile.right != null && tile.right.down != null) {
                    this.right = tile.right.down;
                    tile.right.down.left = this;
                }

                return true;
            }

            if (this.right == null && matchesRight(tile)) {
                this.right = tile;
                tile.left = this;

                if (this.up != null && this.up.right != null) {
                    tile.up = this.up.right;
                    this.up.right.down = tile;
                }

                if (this.down != null && this.down.right != null) {
                    tile.down = this.down.right;
                    this.down.right.up = tile;
                }

                if (tile.up != null && tile.up.left != null) {
                    this.up = tile.up.left;
                    tile.up.left.down = this;
                }

                if (tile.down != null && tile.down.left != null) {
                    this.down = tile.down.left;
                    tile.down.left.up = this;
                }

                return true;
            }

            if (this.down == null && matchesDown(tile)) {
                this.down = tile;
                tile.up = this;

                if (this.left != null && this.left.down != null) {
                    tile.left = this.left.down;
                    this.left.down.right = tile;
                }

                if (this.right != null && this.right.down != null) {
                    tile.right = this.right.down;
                    this.right.down.left = tile;
                }

                if (tile.left != null && tile.left.up != null) {
                    this.left = tile.left.up;
                    tile.left.up.right = this;
                }

                if (tile.right != null && tile.right.up != null) {
                    this.right = tile.right.up;
                    tile.right.up.left = this;
                }

                return true;
            }

            if (this.left == null && matchesLeft(tile)) {
                this.left = tile;
                tile.right = this;

                if (this.up != null && this.up.left != null) {
                    tile.up = this.up.left;
                    this.up.left.down = tile;
                }

                if (this.down != null && this.down.left != null) {
                    tile.down = this.down.left;
                    this.down.left.up = tile;
                }

                if (tile.up != null && tile.up.right != null) {
                    this.up = tile.up.right;
                    tile.up.right.down = this;
                }

                if (tile.down != null && tile.down.right != null) {
                    this.down = tile.down.right;
                    tile.down.right.up = this;
                }

                return true;
            }

            return false;
        }

        private boolean matchesUp(Tile tile) {
            for (int i = 0; i < SIZE; i++) {
                if (this.grid[0][i] != tile.grid[SIZE - 1][i]) {
                    return false;
                }
            }

            return true;
        }

        private boolean matchesRight(Tile tile) {
            for (int i = 0; i < SIZE; i++) {
                if (this.grid[i][SIZE - 1] != tile.grid[i][0]) {
                    return false;
                }
            }

            return true;
        }

        private boolean matchesDown(Tile tile) {
            for (int i = 0; i < SIZE; i++) {
                if (this.grid[SIZE - 1][i] != tile.grid[0][i]) {
                    return false;
                }
            }

            return true;
        }

        private boolean matchesLeft(Tile tile) {
            for (int i = 0; i < SIZE; i++) {
                if (this.grid[i][0] != tile.grid[i][SIZE - 1]) {
                    return false;
                }
            }

            return true;
        }

        private void rotate() {
            final boolean[][] newgrid = new boolean[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    newgrid[i][j] = this.grid[SIZE - j - 1][i];
                }
            }

            this.grid = newgrid;
        }

        private void flip() {
            for (int i = SIZE / 2 - 1; i >= 0; i--) {
                final boolean[] temp = this.grid[i];
                this.grid[i] = this.grid[SIZE - i - 1];
                this.grid[SIZE - i - 1] = temp;
            }
        }
    }
}
