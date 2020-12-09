package day09;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class day09 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day09/day09.txt"));
        final long[] list = new long[input.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = Long.parseLong(input.get(i));
        }

        part1(list);
        part2(list);
    }

    private static void part1(long[] list) {
        System.out.println(getKey(list));
    }

    private static void part2(long[] list) {
        final long key = getKey(list);
        for (int i = 0; i < list.length; i++) {
            long sum = 0;
            for (int j = i; j < list.length; j++) {
                sum += list[j];
                if (sum > key) {
                    break;
                }

                if (sum == key) {
                    long min = Long.MAX_VALUE;
                    long max = Long.MIN_VALUE;
                    for (int k = i; k <= j; k++) {
                        min = Long.min(min, list[k]);
                        max = Long.max(max, list[k]);
                    }

                    System.out.println(min + max);;
                    return;
                }
            }
        }
    }

    private static long getKey(long[] list) {
        for (int i = 0; i < list.length - 25; i++) {
            boolean valid = false;
            for (int j = i; j < i + 25; j++) {
                for (int k = i; k < i + 25; k++) {
                    if (j == k) {
                        continue;
                    }

                    if (list[j] + list[k] == list[i + 25]) {
                        valid = true;
                        j = i + 25;
                        break;
                    }
                }
            }

            if (!valid) {
                return list[i + 25];
            }
        }

        return 0;
    }
}
