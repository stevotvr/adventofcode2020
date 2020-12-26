package com.stevotvr.aoc2020;

import java.util.List;

public class Day03 extends Solution {
    @Override
    public Object getPart1Solution() {
        final boolean[][] grid = getInput();
        return countTrees(grid, 3, 1);
    }

    @Override
    public Object getPart2Solution() {
        final boolean[][] grid = getInput();

        final int[][] moves = { { 1, 1 }, { 3, 1 }, { 5, 1 }, { 7, 1 }, { 1, 2 } };
        long product = 1;
        for (int[] m : moves) {
            product *= countTrees(grid, m[0], m[1]);
        }

        return product;
    }

    private boolean[][] getInput() {
        final List<String> input = getInputLines();
        final boolean[][] grid = new boolean[input.size()][];
        for (int r = 0; r < input.size(); r++) {
            grid[r] = new boolean[input.get(r).length()];
            for (int c = 0; c < input.get(r).length(); c++) {
                grid[r][c] = input.get(r).charAt(c) == '#';
            }
        }

        return grid;
    }

    private static int countTrees(boolean[][] grid, int right, int down) {
        int trees = 0;
        for (int r = 0, c = 0; r < grid.length; r += down, c += right) {
            trees += grid[r][c % grid[r].length] ? 1 : 0;
        }

        return trees;
    }
}
