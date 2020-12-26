package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day05 extends Solution {
    @Override
    public Object getPart1Solution() {
        return Collections.max(getSeatIds(getInputLines()));
    }

    @Override
    public Object getPart2Solution() {
        final List<Integer> seats = getSeatIds(getInputLines());
        Collections.sort(seats);
        int prev = seats.get(0);
        for (int next : seats) {
            if (next - prev > 1) {
                return prev + 1;
            }

            prev = next;
        }

        return null;
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
