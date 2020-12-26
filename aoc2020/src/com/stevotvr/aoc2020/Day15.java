package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day15 extends Solution {
    @Override
    public Object getPart1Solution() {
        return calculate(getInput(), 2020);
    }

    @Override
    public Object getPart2Solution() {
        return calculate(getInput(), 30000000);
    }

    private List<Long> getInput() {
        final String input = getInpuString();
        final List<Long> numbers = new ArrayList<>();
        for (String number : input.split(",")) {
            numbers.add(Long.parseLong(number));
        }

        return numbers;
    }

    private static Long calculate(List<Long> numbers, long target) {
        final HashMap<Long, Long> map = new HashMap<>();
        long i = 0;
        for (; i < numbers.size() - 1; i++) {
            map.put(numbers.get((int) i), i);
        }

        long n = numbers.get((int) i);
        for (; i < target - 1; i++) {
            final Long prev = map.get(n);
            map.put(n, i);
            n = prev == null ? 0 : i - prev;
        }

        return n;
    }
}
