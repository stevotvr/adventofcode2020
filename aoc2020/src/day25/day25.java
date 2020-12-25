package day25;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class day25 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day25/day25.txt"));
        part1(Long.parseLong(input.get(0)), Long.parseLong(input.get(1)));
    }

    private static void part1(long doorkey, long cardkey) {
        long value = 1;
        long loop = 0;
        while (value != doorkey && value != cardkey) {
            value *= 7;
            value %= 20201227;
            loop++;
        }

        final long subj = value == doorkey ? cardkey : doorkey;

        value = 1;
        for (long i = 0; i < loop; i++) {
            value *= subj;
            value %= 20201227;
        }

        System.out.println(value);
    }
}
