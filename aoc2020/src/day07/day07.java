package day07;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class day07 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day07/day07.txt"));
        final HashMap<String, Bag> map = new HashMap<>();
        for (String line : input) {
            final String[] kv = line.split(" bags contain ");
            final String[] values = kv[1].substring(0, kv[1].length() - 1).split(", ");
            if (!map.containsKey(kv[0])) {
                map.put(kv[0], new Bag(kv[0]));
            }

            for (String v : values) {
                if (v.equals("no other bags")) {
                    continue;
                }

                final int count = Integer.parseInt(v.substring(0, v.indexOf(" ")));
                final String name = v.substring(v.indexOf(" ") + 1, v.lastIndexOf(" "));
                if (!map.containsKey(name)) {
                    map.put(name, new Bag(name));
                }

                map.get(kv[0]).addBags(map.get(name), count);
            }
        }

        part1(map);
        part2(map);
    }

    private static void part1(HashMap<String, Bag> bagMap) {
        int total = 0;
        for (Bag b : bagMap.values()) {
            total += b.containsBag("shiny gold") ? 1 : 0;
        }

        System.out.println(total);
    }

    private static void part2(HashMap<String, Bag> bagMap) {
        System.out.println(bagMap.get("shiny gold").countBags());
    }

    private static class Bag {
        private final String name;

        private final HashMap<Bag, Integer> bags = new HashMap<>();

        public Bag(String name) {
            this.name = name;
        }

        public void addBags(Bag bag, int count) {
            this.bags.put(bag, count);
        }

        public boolean containsBag(String name) {
            if (this.name.equals(name)) {
                return false;
            }

            for (Bag b : bags.keySet()) {
                if (b.name.equals(name) || b.containsBag(name)) {
                    return true;
                }
            }
                
            return false;
        }

        public int countBags() {
            int total = 0;
            for (Entry<Bag, Integer> e : this.bags.entrySet()) {
                total += e.getValue() + e.getValue() * e.getKey().countBags();
            }

            return total;
        }
    }
}
