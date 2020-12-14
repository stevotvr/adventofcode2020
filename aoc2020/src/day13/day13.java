package day13;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class day13 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day13/day13.txt"));
        final long minimum = Long.parseLong(input.get(0));
        final List<Long> ids = new ArrayList<>();
        for (String id : input.get(1).split(",")) {
            ids.add(id.equals("x") ? -1 : Long.parseLong(id));
        }

        part1(minimum, ids);
        part2(ids);
    }

    private static void part1(long minimum, List<Long> ids) {
        long depart = minimum;
        while (true) {
            for (long id : ids) {
                if (id < 0) {
                    continue;
                }

                if (depart % id == 0) {
                    System.out.println(id * (depart - minimum));
                    return;
                }
            }

            depart++;
        }
    }

    private static void part2(List<Long> ids) {
        long lcm = -1, time = -1;
        int index = 0;
        while (true) {
            final long id = ids.get(index);
            if (id == -1) {
                index++;
                continue;
            }

            if (lcm == -1) {
                lcm = id;
                time = id - index;
                index++;
                continue;
            }

            if ((time + index) % id == 0) {
                if (++index >= ids.size()) {
                    System.out.println(time);
                    return;
                }

                lcm *= id;
                continue;
            }

            time += lcm;
        }
    }
}
