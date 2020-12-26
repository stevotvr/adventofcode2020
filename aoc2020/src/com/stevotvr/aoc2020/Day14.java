package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 extends Solution {
    @Override
    public Object getPart1Solution() {
        final HashMap<Long, Long> data = new HashMap<>();
        char[] mask = new char[36];
        for (Object instruction : getInput()) {
            if (instruction instanceof char[]) {
                mask = (char[])instruction;
            } else {
                final long[] kv = (long[])instruction;
                data.put(kv[0], applyMask(kv[1], mask));
            }
        }

        return data.values().stream().reduce((a, b) -> a + b).get();
    }

    @Override
    public Object getPart2Solution() {
        final HashMap<Long, Long> data = new HashMap<>();
        char[] mask = new char[36];
        for (Object instruction : getInput()) {
            if (instruction instanceof char[]) {
                mask = (char[])instruction;
            } else {
                final long[] kv = (long[])instruction;
                for (long addr : applyAddressMask(kv[0], mask)) {
                    data.put(addr, kv[1]);
                }
            }
        }

        return data.values().stream().reduce((a, b) -> a + b).get();
    }

    private Object[] getInput() {
        final List<String> input = getInputLines();
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

        return instructions;
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
