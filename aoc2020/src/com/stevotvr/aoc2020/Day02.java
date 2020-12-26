package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Solution {
    @Override
    public Object getPart1Solution() {
        final List<Test> tests = getInput();

        int valid = 0;
        for (Test test : tests) {
            valid += test.isValid() ? 1 : 0;
        }

        return valid;
    }

    @Override
    public Object getPart2Solution() {
        final List<Test> tests = getInput();

        int valid = 0;
        for (Test test : tests) {
            valid += test.isValidNew() ? 1 : 0;
        }

        return valid;
    }

    private List<Test> getInput() {
        final List<String> input = getInputLines();
        final List<Test> tests = new ArrayList<Test>();
        for (String s : input) {
            final String[] parts = s.split(": ");
            tests.add(new Test(new Policy(parts[0]), parts[1]));
        }

        return tests;
    }

    private static class Policy {
        public final int min;
        public final int max;
        public final char character;

        public Policy(String policy) {
            final String[] parts = policy.split("[- ]");
            this.min = Integer.parseInt(parts[0]);
            this.max = Integer.parseInt(parts[1]);
            this.character = parts[2].charAt(0);
        }
    }

    private static class Test {
        private final Policy policy;
        private final char[] password;

        public Test(Policy policy, String password) {
            this.policy = policy;
            this.password = password.toCharArray();
        }

        public boolean isValid() {
            int count = 0;
            for (char c : this.password) {
                if (c == this.policy.character) {
                    count++;

                    if (count > this.policy.max) {
                        return false;
                    }
                }
            }

            return count >= this.policy.min;
        }

        public boolean isValidNew() {
            final int min = this.policy.min - 1;
            final int max = this.policy.max - 1;
            return this.password[min] != this.password[max]
                && (this.password[min] == this.policy.character || this.password[max] == this.policy.character);
        }
    }
}
