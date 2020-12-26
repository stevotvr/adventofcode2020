package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day17 extends Solution {
    @Override
    public Object getPart1Solution() {
        final Grid grid = getInput();
        for (int i = 0; i < 6; i++) {
            grid.tick();;
        }

        return grid.countActive();
    }

    @Override
    public Object getPart2Solution() {
        final Grid grid = getInput();
        for (int i = 0; i < 6; i++) {
            grid.tick4d();;
        }

        return grid.countActive();
    }

    private Grid getInput() {
        final List<String> input = getInputLines();
        final List<Coord4d> grid = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') {
                    grid.add(new Coord4d(j, i, 0, 0));
                }
            }
        }

        return new Grid(grid);
    }

    private static class Grid {
        private int minX = Integer.MAX_VALUE;
        private int minY = Integer.MAX_VALUE;
        private int minZ = Integer.MAX_VALUE;
        private int minW = Integer.MAX_VALUE;
        private int maxX = Integer.MIN_VALUE;
        private int maxY = Integer.MIN_VALUE;
        private int maxZ = Integer.MIN_VALUE;
        private int maxW = Integer.MIN_VALUE;
        private HashSet<Coord4d> grid = new HashSet<>();

        public Grid(List<Coord4d> grid) {
            for (Coord4d coord : grid) {
                setActive(this.grid, coord);
            }
        }

        public int countActive() {
            return this.grid.size();
        }

        public void tick() {
            final HashSet<Coord4d> grid2 = new HashSet<>();
            for (int x = this.minX, mx = this.maxX; x <= mx; x++) {
                for (int y = this.minY, my = this.maxY; y <= my; y++) {
                    for (int z = this.minZ, mz = this.maxZ; z <= mz; z++) {
                        final Coord4d coord = new Coord4d(x, y, z, 0);
                        final int neighbors = countNeighbors(coord);
                        final boolean status = this.grid.contains(coord);
                        if (status && (neighbors == 2 || neighbors == 3)) {
                            setActive(grid2, coord);
                        } else if (!status && neighbors == 3) {
                            setActive(grid2, coord);
                        }
                    }
                }
            }

            this.grid = grid2;
        }

        public void tick4d() {
            final HashSet<Coord4d> grid2 = new HashSet<>();
            for (int x = this.minX, mx = this.maxX; x <= mx; x++) {
                for (int y = this.minY, my = this.maxY; y <= my; y++) {
                    for (int z = this.minZ, mz = this.maxZ; z <= mz; z++) {
                        for (int w = this.minW, mw = this.maxW; w <= mw; w++) {
                            final Coord4d coord = new Coord4d(x, y, z, w);
                            final int neighbors = countNeighbors4d(coord);
                            final boolean status = this.grid.contains(coord);
                            if (status && (neighbors == 2 || neighbors == 3)) {
                                setActive(grid2, coord);
                            } else if (!status && neighbors == 3) {
                                setActive(grid2, coord);
                            }
                        }
                    }
                }
            }

            this.grid = grid2;
        }

        private void setActive(HashSet<Coord4d> grid, Coord4d coord) {
            this.minX = Math.min(this.minX, coord.x - 1);
            this.minY = Math.min(this.minY, coord.y - 1);
            this.minZ = Math.min(this.minZ, coord.z - 1);
            this.minW = Math.min(this.minW, coord.w - 1);
            this.maxX = Math.max(this.maxX, coord.x + 1);
            this.maxY = Math.max(this.maxY, coord.y + 1);
            this.maxZ = Math.max(this.maxZ, coord.z + 1);
            this.maxW = Math.max(this.maxW, coord.w + 1);
            
            grid.add(coord);
        }

        private int countNeighbors(Coord4d coord) {
            int count = 0;
            for (int x = coord.x - 1; x <= coord.x + 1; x++) {
                for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                    for (int z = coord.z - 1; z <= coord.z + 1; z++) {
                        if (x == coord.x && y == coord.y && z == coord.z) {
                            continue;
                        }

                        count += this.grid.contains(new Coord4d(x, y, z, 0)) ? 1 : 0;
                    }
                }
            }

            return count;
        }

        private int countNeighbors4d(Coord4d coord) {
            int count = 0;
            for (int x = coord.x - 1; x <= coord.x + 1; x++) {
                for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                    for (int z = coord.z - 1; z <= coord.z + 1; z++) {
                        for (int w = coord.w - 1; w <= coord.w + 1; w++) {
                            if (x == coord.x && y == coord.y && z == coord.z && w == coord.w) {
                                continue;
                            }

                            count += this.grid.contains(new Coord4d(x, y, z, w)) ? 1 : 0;
                        }
                    }
                }
            }

            return count;
        }
    }

    private static class Coord4d {
        public final int x;
        public final int y;
        public final int z;
        public final int w;

        public Coord4d(int x, int y, int z, int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Coord4d && this.x == ((Coord4d)obj).x && this.y == ((Coord4d)obj).y && this.z == ((Coord4d)obj).z && this.w == ((Coord4d)obj).w;
        }

        @Override
        public int hashCode() {
            int code = 1;
            code = 31 * code + this.x;
            code = 31 * code + this.y;
            code = 31 * code + this.z;
            code = 31 * code + this.w;
            
            return code;
        }
    }
}
