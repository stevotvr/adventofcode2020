package day06;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;

public class day06 {

    public static void main(String[] args) throws Exception {
        final String input = Files.readString(Path.of("aoc2020/src/day06/day06.txt"));
        final String[] groups = input.split("\n\n");

        part1(groups);
        part2(groups);
    }

    private static void part1(String[] groups) {
        int total = 0;
        for(String group : groups) {
            group = group.replaceAll("[^a-z]", "");
            final HashSet<Character> set = new HashSet<>();
            for (char c : group.toCharArray()) {
                set.add(c);
            }

            total += set.size();
        }

        System.out.println(total);
    }

    private static void part2(String[] groups) {
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

        System.out.println(total);
    }
}
