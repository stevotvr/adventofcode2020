package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day19 extends Solution {
    @Override
    public Object getPart1Solution() {
        final InputData inputData = getInput();

        String regex = inputData.rules.get(0);
        while (!regex.matches("^[a-z \"\\|\\(\\)]+$")) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(inputData.rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();
        }

        final String pattern = "^" + regex.replaceAll("[ \"]", "") + "$";

        return inputData.messages.stream().filter(a -> a.matches(pattern)).count();
    }

    @Override
    public Object getPart2Solution() {
        final InputData inputData = getInput();

        inputData.rules.put(8, "42 | 42 8");
        inputData.rules.put(11, "42 31 | 42 11 31");

        String regex = inputData.rules.get(0);
        long prev = 0;
        while (true) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(inputData.rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();

            final String pattern = "^" + regex.replaceAll("([ \"])|42|31", "") + "$";

            final long count = inputData.messages.stream().filter(a -> a.matches(pattern)).count();
            if (count > 0 && count == prev) {
                return count;
            }
            
            prev = count;
        }
    }

    private InputData getInput() {
        final List<String> input = getInputLines();
        final HashMap<Integer, String> rules = new HashMap<>();
        int i = 0;
        for (;; i++) {
            final String line = input.get(i);
            if (line.isEmpty()) {
                i++;
                break;
            }

            final String[] parts = line.split(": ");
            rules.put(Integer.parseInt(parts[0]), parts[1]);
        }

        final List<String> messages = new ArrayList<>();
        for (; i < input.size(); i++) {
            messages.add(input.get(i));
        }

        return new InputData(rules, messages);
    }

    private static class InputData {
        public final HashMap<Integer, String> rules;
        public final List<String> messages;

        public InputData(HashMap<Integer, String> rules, List<String> messages) {
            this.rules = rules;
            this.messages = messages;
        }
    }
}
