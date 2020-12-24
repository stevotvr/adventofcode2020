package day23;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class day23 {

    public static void main(String[] args) throws Exception {
        final String input = Files.readString(Path.of("aoc2020/src/day23/day23.txt"));
        final List<Integer> cups = new ArrayList<>();
        for (String c : input.split("")) {
            cups.add(Integer.parseInt(c));
        }

        part1(cups);
        part2(cups);
    }

    private static void part1(List<Integer> cups) {
        final Deque<Integer> queue = new ArrayDeque<>(cups);
        final int max = Collections.max(cups);
        for (int i = 0; i < 100; i++) {
            final int current = queue.removeFirst();
            final Deque<Integer> selected = new ArrayDeque<>();
            for (int j = 0; j < 3; j++) {
                selected.addLast(queue.removeFirst());
            }

            int dest = current - 1;
            dest = dest < 1 ? max : dest;
            while (selected.contains(dest)) {
                dest--;
                dest = dest < 1 ? max : dest;
            }

            final Deque<Integer> temp = new ArrayDeque<>();
            temp.addLast(current);
            while (temp.peekLast() != dest) {
                temp.addLast(queue.removeFirst());
            }

            while (!selected.isEmpty()) {
                queue.addFirst(selected.removeLast());
            }

            while (temp.size() > 1) {
                queue.addFirst(temp.removeLast());
            }

            queue.addLast(temp.removeLast());
        }

        while (queue.peekFirst() != 1) {
            queue.addLast(queue.removeFirst());
        }

        queue.removeFirst();

        System.out.println(queue.stream().map(a -> a.toString()).collect(Collectors.joining("")));
    }

    private static void part2(List<Integer> cups) {
        final int max = 1000000;

        final Cup[] cupMap = new Cup[max + 1];

        Cup head = new Cup(cups.get(0));
        cupMap[head.value] = head;
        Cup tail = head;

        for (int i = 1; i < cups.size(); i++) {
            final Cup c = new Cup(cups.get(i));
            cupMap[c.value] = c;
            c.next = head;
            tail.next = c;
            tail = c;
        }

        for (int i = Collections.max(cups) + 1; i <= max; i++) {
            final Cup c = new Cup(i);
            cupMap[c.value] = c;
            c.next = head;
            tail.next = c;
            tail = c;
        }

        for (int i = 0; i < 10000000; i++) {
            final Cup current = head;
            final Cup c1 = current.next;
            final Cup c3 = c1.next.next;

            head.next = c3.next;

            int targetIndex = current.value == 1 ? max : current.value - 1;
            while (targetIndex == c1.value || targetIndex == c1.next.value || targetIndex == c3.value) {
                targetIndex--;
                targetIndex = targetIndex < 1 ? max : targetIndex;
            }

            final Cup target = cupMap[targetIndex];

            c3.next = target.next;
            target.next = c1;
            tail = current;
            head = current.next;
        }

        final Cup one = cupMap[1];
        System.out.println((long)one.next.value * one.next.next.value);
    }

    private static class Cup {
        public final int value;

        public Cup next;

        public Cup(int value) {
            this.value = value;
        }
    }
}
