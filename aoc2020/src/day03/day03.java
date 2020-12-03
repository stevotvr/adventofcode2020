package day03;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class day03 {
    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day03/day03.txt"));
        final boolean[][] grid = new boolean[input.size()][];
        for (int r = 0; r < input.size(); r++) {
            grid[r] = new boolean[input.get(r).length()];
            for (int c = 0; c < input.get(r).length(); c++) {
                grid[r][c] = input.get(r).charAt(c) == '#';
            }
        }

        part1(grid);
        part2(grid);
    }

    private static void part1(boolean[][] grid) {
        System.out.println(countTrees(grid, 3, 1));
    }

    private static void part2(boolean[][] grid) {
        final int[][] moves = { { 1, 1 }, { 3, 1 }, { 5, 1 }, { 7, 1 }, { 1, 2 } };
        long product = 1;
        for (int[] m : moves) {
            product *= countTrees(grid, m[0], m[1]);
        }

        System.out.println(product);
    }

    private static int countTrees(boolean[][] grid, int right, int down) {
        int trees = 0;
        for (int r = 0, c = 0; r < grid.length; r += down, c += right) {
            trees += grid[r][c % grid[r].length] ? 1 : 0;
        }

        return trees;
    }
}
