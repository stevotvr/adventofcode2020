package day01;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class day01 {
    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day01/day01.txt"));
        final List<Integer> ints = new ArrayList<Integer>();
        for (String i : input) {
            ints.add(Integer.parseInt(i));
        }

        part1(ints);
        part2(ints);
    }

    private static void part1(List<Integer> ints) {
        for (int i = 0; i < ints.size(); i++) {
            for (int j = 0; j < ints.size(); j++) {
                if (i == j) {
                    continue;
                }

                if (ints.get(i) + ints.get(j) == 2020) {
                    System.out.println(ints.get(i) * ints.get(j));
                    return;
                }
            }
        }
    }

    private static void part2(List<Integer> ints) {
        for (int i = 0; i < ints.size(); i++) {
            for (int j = 0; j < ints.size(); j++) {
                for (int k = 0; k < ints.size(); k++) {
                    if (i == j || i == k || j == k) {
                        continue;
                    }

                    if (ints.get(i) + ints.get(j) + ints.get(k) == 2020) {
                        System.out.println(ints.get(i) * ints.get(j) * ints.get(k));
                        return;
                    }
                }
            }
        }
    }
}
