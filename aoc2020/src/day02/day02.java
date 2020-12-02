package day02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class day02 {
    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day02/day02.txt"));
        final List<Test> tests = new ArrayList<Test>();
        for (String s : input) {
            final String[] parts = s.split(": ");
            tests.add(new Test(new Policy(parts[0]), parts[1]));
        }

        part1(tests);
        part2(tests);
    }

    private static void part1(List<Test> tests) {
        int valid = 0;
        for (Test test : tests) {
            valid += test.isValid() ? 1 : 0;
        }

        System.out.println(valid);
    }

    private static void part2(List<Test> tests) {
        int valid = 0;
        for (Test test : tests) {
            valid += test.isValidNew() ? 1 : 0;
        }

        System.out.println(valid);
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
