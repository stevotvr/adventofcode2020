package day11;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class day11 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day11/day11.txt"));
        final Grid grid = new Grid(input.size(), input.get(0).length());
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == 'L') {
                    grid.set(i, j, Grid.Status.EMPTY);
                } else if (input.get(i).charAt(j) == '#') {
                    grid.set(i, j, Grid.Status.OCCUPIED);
                } else {
                    grid.set(i, j, Grid.Status.FLOOR);
                }
            }
        }

        part1(grid);
        part2(grid);
    }

    private static void part1(Grid grid) {
        while (true) {
            final Grid next = grid.getNext();
            if (grid.equals(next)) {
                System.out.println(grid.countSeats(Grid.Status.OCCUPIED));
                return;
            }
            
            grid = next;
        }
    }

    private static void part2(Grid grid) {
        while (true) {
            final Grid next = grid.getNextVisibility();
            if (grid.equals(next)) {
                System.out.println(grid.countSeats(Grid.Status.OCCUPIED));
                return;
            }
            
            grid = next;
        }
    }

    private static class Grid {
        public static enum Status {
            FLOOR,
            EMPTY,
            OCCUPIED
        };

        private static int[][] offsets = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1} };

        private final Status[][] grid;

        public Grid(int rows, int cols) {
            this.grid = new Status[rows][cols];
        }

        public void set(int row, int col, Status status) {
            this.grid[row][col] = status;
        }

        public Grid getNext() {
            final Grid next = new Grid(this.grid.length, this.grid[0].length);
            for (int i = 0; i < this.grid.length; i++) {
                for (int j = 0; j < this.grid[i].length; j++) {
                    if (this.grid[i][j] == Status.EMPTY && countAdjacent(i, j) == 0) {
                        next.set(i, j, Status.OCCUPIED);
                    } else if (this.grid[i][j] == Status.OCCUPIED && countAdjacent(i, j) >= 4) {
                        next.set(i, j, Status.EMPTY);
                    } else {
                        next.set(i, j, this.grid[i][j]);
                    }
                }
            }

            return next;
        }

        public Grid getNextVisibility() {
            final Grid next = new Grid(this.grid.length, this.grid[0].length);
            for (int i = 0; i < this.grid.length; i++) {
                for (int j = 0; j < this.grid[i].length; j++) {
                    if (this.grid[i][j] == Status.EMPTY && countVisible(i, j) == 0) {
                        next.set(i, j, Status.OCCUPIED);
                    } else if (this.grid[i][j] == Status.OCCUPIED && countVisible(i, j) >= 5) {
                        next.set(i, j, Status.EMPTY);
                    } else {
                        next.set(i, j, this.grid[i][j]);
                    }
                }
            }

            return next;
        }

        public int countSeats(Status status) {
            int count = 0;
            for (Status[] row : this.grid) {
                for (Status seat : row) {
                    count += seat == status ? 1 : 0;
                }
            }

            return count;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Grid)) {
                return false;
            }

            final Grid obj2 = (Grid)obj;
            if (this.grid.length != obj2.grid.length || this.grid[0].length != obj2.grid[0].length) {
                return false;
            }

            for (int i = 0; i < this.grid.length; i++) {
                for (int j = 0; j < this.grid[i].length; j++) {
                    if (this.grid[i][j] != obj2.grid[i][j]) {
                        return false;
                    }
                }
            }

            return true;
        }

        private int countAdjacent(int row, int col) {
            int count = 0;
            for (int[] offset : offsets) {
                final int row2 = row + offset[0];
                final int col2 = col + offset[1];
                if (row2 < 0 || row2 >= this.grid.length || col2 < 0 || col2 >= this.grid[row2].length) {
                    continue;
                }

                if (this.grid[row2][col2] == Status.OCCUPIED) {
                    count++;
                }
            }

            return count;
        }

        private int countVisible(int row, int col) {
            int count = 0;
            for (int[] offset : offsets) {
                int row2 = row;
                int col2 = col;
                while (true) {
                    row2 += offset[0];
                    col2 += offset[1];
                    if (row2 < 0 || row2 >= this.grid.length || col2 < 0 || col2 >= this.grid[row2].length) {
                        break;
                    }
    
                    if (this.grid[row2][col2] == Status.EMPTY) {
                        break;
                    }
    
                    if (this.grid[row2][col2] == Status.OCCUPIED) {
                        count++;
                        break;
                    }
                }
            }

            return count;
        }
    }
}
