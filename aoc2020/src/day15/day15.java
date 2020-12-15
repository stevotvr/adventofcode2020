package day15;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day15 {

    public static void main(String[] args) throws Exception {
        final String input = Files.readString(Path.of("aoc2020/src/day15/day15.txt"));
        final List<Long> numbers = new ArrayList<>();
        for (String number : input.split(",")) {
            numbers.add(Long.parseLong(number));
        }

        part1(numbers);
        part2(numbers);
    }

    private static void part1(List<Long> numbers) {
        System.out.println(calculate(numbers, 2020));
    }

    private static void part2(List<Long> numbers) {
        System.out.println(calculate(numbers, 30000000));
    }

    private static Long calculate(List<Long> numbers, long target) {
        final HashMap<Long, Long> map = new HashMap<>();
        long i = 0;
        for (; i < numbers.size() - 1; i++) {
            map.put(numbers.get((int) i), (long) i);
        }

        long n = numbers.get((int) i);
        for (; i < target - 1; i++) {
            final Long prev = map.get(n);
            map.put(n, i);
            if (prev == null) {
                n = 0;
            } else {
                n = i - prev;
            }
            
        }

        return n;
    }
}
