package com.stevotvr.aoc2020;

import java.util.List;

public class Day09 extends Solution {
    @Override
    public Object getPart1Solution() {
        return getKey(getInput());
    }

    @Override
    public Object getPart2Solution() {
        final long[] list = getInput();

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

                    return min + max;
                }
            }
        }

        return null;
    }

    private long[] getInput() {
        final List<String> input = getInputLines();
        final long[] list = new long[input.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = Long.parseLong(input.get(i));
        }

        return list;
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
