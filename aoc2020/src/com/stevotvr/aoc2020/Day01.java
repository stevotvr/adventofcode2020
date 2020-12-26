package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.List;

public class Day01 extends Solution {
    @Override
    public Object getPart1Solution() {
        final List<Integer> ints = getInput();

        for (int i = 0; i < ints.size(); i++) {
            for (int j = 0; j < ints.size(); j++) {
                if (i == j) {
                    continue;
                }

                if (ints.get(i) + ints.get(j) == 2020) {
                    return ints.get(i) * ints.get(j);
                }
            }
        }

        return null;
    }

    @Override
    public Object getPart2Solution() {
        final List<Integer> ints = getInput();
        
        for (int i = 0; i < ints.size(); i++) {
            for (int j = 0; j < ints.size(); j++) {
                for (int k = 0; k < ints.size(); k++) {
                    if (i == j || i == k || j == k) {
                        continue;
                    }

                    if (ints.get(i) + ints.get(j) + ints.get(k) == 2020) {
                        return ints.get(i) * ints.get(j) * ints.get(k);
                    }
                }
            }
        }

        return null;
    }

    private List<Integer> getInput() {
        final List<String> input = getInputLines();
        final List<Integer> ints = new ArrayList<Integer>();
        for (String i : input) {
            ints.add(Integer.parseInt(i));
        }

        return ints;
    }
}
