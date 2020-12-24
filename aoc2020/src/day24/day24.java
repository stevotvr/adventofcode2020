package day24;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day24 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day24/day24.txt"));
        final List<List<Integer>> instructions = new ArrayList<>();
        for (String line : input) {
            final List<Integer> path = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case 'e':
                        path.add(0);
                        break;
                    case 's':
                        path.add(line.charAt(i + 1) == 'e' ? 1 : 2);
                        i++;
                        break;
                    case 'w':
                        path.add(3);
                        break;
                    case 'n':
                        path.add(line.charAt(i + 1) == 'w' ? 4 : 5);
                        i++;
                        break;
                }
            }

            instructions.add(path);
        }

        part1(instructions);
        part2(instructions);
    }

    private static void part1(List<List<Integer>> instructions) {
        final HashMap<Coord, Boolean> tiles = new HashMap<>();
        final int[][] directions = new int[][] { { 1, -1, 0 }, { 0, -1, 1 }, { -1, 0, 1 }, { -1, 1, 0}, { 0, 1, -1 }, { 1, 0, -1 } };

        for (List<Integer> path : instructions) {
            final Coord pos = new Coord(0, 0, 0);
            for (int d : path) {
                pos.x += directions[d][0];
                pos.y += directions[d][1];
                pos.z += directions[d][2];
            }

            if (!tiles.containsKey(pos)) {
                tiles.put(pos, true);
            } else {
                tiles.put(pos, !tiles.get(pos));
            }
        }

        System.out.println(tiles.values().stream().filter(a -> a).count());
    }

    private static void part2(List<List<Integer>> instructions) {
        HashMap<Coord, Boolean> tiles = new HashMap<>();
        final int[][] directions = new int[][] { { 1, -1, 0 }, { 0, -1, 1 }, { -1, 0, 1 }, { -1, 1, 0}, { 0, 1, -1 }, { 1, 0, -1 } };

        for (List<Integer> path : instructions) {
            final Coord pos = new Coord(0, 0, 0);
            for (int d : path) {
                pos.x += directions[d][0];
                pos.y += directions[d][1];
                pos.z += directions[d][2];
            }

            if (!tiles.containsKey(pos)) {
                tiles.put(pos, true);
            } else {
                tiles.put(pos, !tiles.get(pos));
            }
        }

        int minX = tiles.keySet().stream().mapToInt(a -> a.x).min().getAsInt();
        int minY = tiles.keySet().stream().mapToInt(a -> a.y).min().getAsInt();
        int minZ = tiles.keySet().stream().mapToInt(a -> a.z).min().getAsInt();
        int maxX = tiles.keySet().stream().mapToInt(a -> a.x).max().getAsInt();
        int maxY = tiles.keySet().stream().mapToInt(a -> a.y).max().getAsInt();
        int maxZ = tiles.keySet().stream().mapToInt(a -> a.z).max().getAsInt();

        HashMap<Coord, Boolean> temp;
        final Coord pos = new Coord(0, 0, 0);
        for (int i = 0; i < 100; i++) {
            temp = new HashMap<>();
            for (int x = minX - 1; x <= maxX + 1; x++) {
                for (int y = minY - 1; y <= maxY + 1; y++) {
                    for (int z = minZ - 1; z <= maxZ + 1; z++) {
                        final Coord current = new Coord(x, y, z);
                        final boolean value = tiles.containsKey(current) && tiles.get(current);

                        int adj = 0;
                        for (int[] d : directions) {
                            pos.x = x + d[0];
                            pos.y = y + d[1];
                            pos.z = z + d[2];

                            adj += (tiles.containsKey(pos) && tiles.get(pos)) ? 1 : 0;
                        }
                        
                        if (value && (adj == 0 || adj > 2)) {
                            temp.put(current, false);
                        } else if (!value && adj == 2) {
                            temp.put(current, true);
                            minX = Math.min(minX, current.x);
                            minY = Math.min(minY, current.y);
                            minZ = Math.min(minZ, current.z);
                            maxX = Math.max(maxX, current.x);
                            maxY = Math.max(maxY, current.y);
                            maxZ = Math.max(maxZ, current.z);
                        } else {
                            temp.put(current, value);
                        }
                    }
                }
            }

            tiles = temp;
        }

        System.out.println(tiles.values().stream().filter(a -> a).count());
    }

    final static class Coord {
        public int x;
        public int y;
        public int z;

        public Coord(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Coord && ((Coord)obj).x == this.x && ((Coord)obj).y == this.y && ((Coord)obj).z == this.z;
        }

        @Override
        public int hashCode() {
            int code = 1;
            code = 31 * code + this.x;
            code = 31 * code + this.y;
            code = 31 * code + this.z;
            
            return code;
        }
    }
}
