package com.stevotvr.aoc2020;

import java.util.Arrays;
import java.util.List;

public class Day10 extends Solution {
    @Override
    public Object getPart1Solution() {
        final int[] list = getInput();

        int d1 = 0;
        int d3 = 1;
        for (int i = 0; i < list.length; i++) {
            final int prev = i == 0 ? 0 : list[i - 1];
            final int d = list[i] - prev;
            if (d == 1) {
                d1++;
            } else if (d == 3) {
                d3++;
            }
        }

        return d1 * d3;
    }

    @Override
    public Object getPart2Solution() {
        final int[] list = getInput();

        final long[] sums = new long[list[list.length - 1] + 1];
        sums[0] = 1;
        for (int i = 0; i < list.length; i++) {
            final long x = list[i] >= 3 ? sums[list[i] - 3] : 0;
            final long y = list[i] >= 2 ? sums[list[i] - 2] : 0;
            final long z = list[i] >= 1 ? sums[list[i] - 1] : 0;
            sums[list[i]] = x + y + z;
        }

        return sums[sums.length - 1];
    }

    public int[] getInput() {
        final List<String> input = getInputLines();
        final int[] list = new int[input.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = Integer.parseInt(input.get(i));
        }

        Arrays.sort(list);

        return list;
    }
}
