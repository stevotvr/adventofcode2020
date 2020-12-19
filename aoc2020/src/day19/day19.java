package day19;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day19 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day19/day19.txt"));
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

        part1(rules, messages);
        part2(rules, messages);
    }

    private static void part1(HashMap<Integer, String> rules, List<String> messages) {
        String regex = rules.get(0);
        while (!regex.matches("^[a-z \"\\|\\(\\)]+$")) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();
        }

        final String pattern = "^" + regex.replaceAll("[ \"]", "") + "$";

        System.out.println(messages.stream().filter(a -> a.matches(pattern)).count());
    }

    private static void part2(HashMap<Integer, String> rules, List<String> messages) {
        rules.put(8, "42 | 42 8");
        rules.put(11, "42 31 | 42 11 31");

        String regex = rules.get(0);
        long prev = 0;
        while (true) {
            final StringBuilder builder = new StringBuilder();
            for (String part : regex.split(" ")) {
                if (part.matches("[0-9]+")) {
                    builder.append("( ").append(rules.get(Integer.parseInt(part))).append(" )");
                } else {
                    builder.append(part).append(' ');
                }
            }

            regex = builder.toString();

            final String pattern = "^" + regex.replaceAll("([ \"])|42|31", "") + "$";

            final long count = messages.stream().filter(a -> a.matches(pattern)).count();
            if (count > 0 && count == prev) {
                System.out.println(count);
                return;
            }
            
            prev = count;
        }
    }
}
