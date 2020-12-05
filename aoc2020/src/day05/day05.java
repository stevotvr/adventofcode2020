package day05;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day05 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day05/day05.txt"));

        part1(input);
        part2(input);
    }

    private static void part1(List<String> paths) {
        System.out.println(Collections.max(getSeatIds(paths)));
    }

    private static void part2(List<String> paths) {
        final List<Integer> seats = getSeatIds(paths);
        Collections.sort(seats);
        int prev = seats.get(0);
        for (int next : seats) {
            if (next - prev > 1) {
                System.out.println(prev + 1);
                return;
            }

            prev = next;
        }
    }

    private static List<Integer> getSeatIds(List<String> paths) {
        final List<Integer> list = new ArrayList<>();
        for (String path : paths) {
            int min = 0;
            int max = 127;
            for (int i = 0; i < 7; i++) {
                if (path.charAt(i) == 'F') {
                    max -= (max - min) / 2f;
                } else {
                    min += Math.ceil((max - min) / 2f);
                }
            }

            final int row = min;

            min = 0;
            max = 7;
            for (int i = 7; i < 10; i++) {
                if (path.charAt(i) == 'L') {
                    max -= (max - min) / 2f;
                } else {
                    min += Math.ceil((max - min) / 2f);
                }
            }

            list.add(row * 8 + min);
        }

        return list;
    }
}
