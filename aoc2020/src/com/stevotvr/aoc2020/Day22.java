package com.stevotvr.aoc2020;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22 extends Solution {
    @Override
    public Object getPart1Solution() {
        final InputData inputData = getInput();

        final Deque<Integer> d1 = inputData.player1;
        final Deque<Integer> d2 = inputData.player2;
        while (!d1.isEmpty() && !d2.isEmpty()) {
            final int c1 = d1.removeFirst();
            final int c2 = d2.removeFirst();
            if (c1 > c2) {
                d1.addLast(c1);
                d1.addLast(c2);
            } else {
                d2.addLast(c2);
                d2.addLast(c1);
            }
        }

        return getScore(d2.isEmpty() ? d1 : d2);
    }

    @Override
    public Object getPart2Solution() {
        final InputData inputData = getInput();

        final Deque<Integer> d1 = inputData.player1;
        final Deque<Integer> d2 = inputData.player2;
        final Deque<Integer> winner = new ArrayDeque<>();
        getWinner(d1, d2, winner, d1.size(), d2.size());

        return getScore(winner);
    }

    private InputData getInput() {
        final List<String> input = getInputLines();
        final Deque<Integer> player1 = new ArrayDeque<>();
        final Deque<Integer> player2 = new ArrayDeque<>();
        Deque<Integer> p = player1;
        for (String line : input) {
            if (line.isEmpty() || line.equals("Player 1:")) {
                continue;
            }

            if (line.equals("Player 2:")) {
                p = player2;
                continue;
            }

            p.add(Integer.parseInt(line));
        }

        return new InputData(player1, player2);
    }

    private static class InputData {
        public final Deque<Integer> player1;
        public final Deque<Integer> player2;

        public InputData(Deque<Integer> player1, Deque<Integer> player2) {
            this.player1 = player1;
            this.player2 = player2;
        }
    }

    private static int getScore( Deque<Integer> winner) {
        int mult = 1;
        int score = 0;
        while (!winner.isEmpty()) {
            score += winner.removeLast() * mult++;
        }

        return score;
    }

    private static Deque<Integer> getWinner(Deque<Integer> player1, Deque<Integer> player2, Deque<Integer> winner, int l1, int l2) {
        final Deque<Integer> d1 = new ArrayDeque<>(player1);
        final Deque<Integer> d2 = new ArrayDeque<>(player2);

        for (int i = player1.size() - l1; i > 0; i--) {
            d1.removeLast();
        }

        for (int i = player2.size() - l2; i > 0; i--) {
            d2.removeLast();
        }

        final Set<String> history = new HashSet<>();
        while (!d1.isEmpty() && !d2.isEmpty()) {
            final String hash = d1.toString() + d2.toString();
            if (!history.add(hash)) {
                return player1;
            }

            final int c1 = d1.removeFirst();
            final int c2 = d2.removeFirst();
            if (d1.size() >= c1 && d2.size() >= c2) {
                if (getWinner(d1, d2, null, c1, c2) == d1) {
                    d1.addLast(c1);
                    d1.addLast(c2);
                } else {
                    d2.addLast(c2);
                    d2.addLast(c1);
                }
            } else {
                if (c1 > c2) {
                    d1.addLast(c1);
                    d1.addLast(c2);
                } else {
                    d2.addLast(c2);
                    d2.addLast(c1);
                }
            }
        }

        if (winner != null) {
            winner.addAll(d2.isEmpty() ? d1 : d2);
        }

        return d2.isEmpty() ? player1 : player2;
    }
}
