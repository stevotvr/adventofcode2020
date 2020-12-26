package com.stevotvr.aoc2020;

import java.util.HashMap;
import java.util.HashSet;

public class Day06 extends Solution {
    @Override
    public Object getPart1Solution() {
        final String[] groups = getInput();

        int total = 0;
        for(String group : groups) {
            group = group.replaceAll("[^a-z]", "");
            final HashSet<Character> set = new HashSet<>();
            for (char c : group.toCharArray()) {
                set.add(c);
            }

            total += set.size();
        }

        return total;
    }

    @Override
    public Object getPart2Solution() {
        final String[] groups = getInput();
        
        int total = 0;
        for(String group : groups) {
            final String[] persons = group.split("[^a-z]");
            group = String.join("", persons);
            final HashMap<Character, Integer> map = new HashMap<>();
            for (char c : group.toCharArray()) {
                map.put(c, map.containsKey(c) ? map.get(c) + 1 : 1);
            }

            for (int count : map.values()) {
                total += count == persons.length ? 1 : 0;
            }
        }

        return total;
    }

    private String[] getInput() {
        final String input = getInpuString();
        return input.split("\n\n");
    }
}
