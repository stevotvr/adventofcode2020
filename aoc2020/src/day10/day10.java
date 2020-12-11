package day10;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class day10 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day10/day10.txt"));
        final int[] list = new int[input.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = Integer.parseInt(input.get(i));
        }

        Arrays.sort(list);

        part1(list);
        part2(list);
    }

    private static void part1(int[] list) {
        int d1 = 0;
        int d3 = 1;
        for (int i = 0; i < list.length; i++) {
            final int prev = i == 0 ? 0 : list[i - 1];
            final int d = list[i] - prev;
            if (d == 1) {
                d1++;
            } else if (d == 3) {
                d3++;
            }
        }

        System.out.println(d1 * d3);
    }

    private static void part2(int[] list) {
        final long[] sums = new long[list[list.length - 1] + 1];
        sums[0] = 1;
        for (int i = 0; i < list.length; i++) {
            final long x = list[i] >= 3 ? sums[list[i] - 3] : 0;
            final long y = list[i] >= 2 ? sums[list[i] - 2] : 0;
            final long z = list[i] >= 1 ? sums[list[i] - 1] : 0;
            sums[list[i]] = x + y + z;
        }

        System.out.println(sums[sums.length - 1]);
    }
}
