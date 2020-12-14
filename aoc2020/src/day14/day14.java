package day14;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class day14 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day14/day14.txt"));
        final Object[] instructions = new Object[input.size()];
        for (int i = 0; i < instructions.length; i++) {
            final String line = input.get(i);
            if (line.charAt(1) == 'a') {
                instructions[i] = line.substring(line.lastIndexOf(' ') + 1).toCharArray();
            } else {
                final long k = Long.parseLong(line.substring(line.indexOf('[') + 1, line.indexOf(']')));
                final long v = Long.parseLong(line.substring(line.lastIndexOf(' ') + 1));
                instructions[i] = new long[] { k, v };
            }
        }

        part1(instructions);
        part2(instructions);
    }

    private static void part1(Object[] instructions) {
        final HashMap<Long, Long> data = new HashMap<>();
        char[] mask = new char[36];
        for (Object instruction : instructions) {
            if (instruction instanceof char[]) {
                mask = (char[])instruction;
            } else {
                final long[] kv = (long[])instruction;
                data.put(kv[0], applyMask(kv[1], mask));
            }
        }

        System.out.println(data.values().stream().reduce((a, b) -> a + b).get());
    }

    private static void part2(Object[] instructions) {
        final HashMap<Long, Long> data = new HashMap<>();
        char[] mask = new char[36];
        for (Object instruction : instructions) {
            if (instruction instanceof char[]) {
                mask = (char[])instruction;
            } else {
                final long[] kv = (long[])instruction;
                for (long addr : applyAddressMask(kv[0], mask)) {
                    data.put(addr, kv[1]);
                }
            }
        }

        System.out.println(data.values().stream().reduce((a, b) -> a + b).get());
    }

    private static long applyMask(long n, char[] mask) {
        for (int i = 0; i < mask.length; i++) {
            final long bit = 1l << (mask.length - i - 1);
            if (mask[i] == '1') {
                n |= bit;
            } else if (mask[i] == '0') {
                n &= ~bit;
            }
        }

        return n;
    }

    private static List<Long> applyAddressMask(long addr, char[] mask) {
        List<Long> addrs = new ArrayList<>();
        addrs.add(addr);
        for (int i = 0; i < mask.length; i++) {
            final long bit = 1l << (mask.length - i - 1);
            if (mask[i] != '0') {
                addrs = addrs.stream().map(a -> a | bit).collect(Collectors.toList());
                
                if (mask[i] == 'X') {
                    addrs.addAll(addrs.stream().map(a -> a & ~bit).collect(Collectors.toList()));
                }
            }
        }

        return addrs.stream().distinct().collect(Collectors.toList());
    }
}
