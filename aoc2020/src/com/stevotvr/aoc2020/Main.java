package com.stevotvr.aoc2020;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to StevoTVR's puzzle solver for Advent of Code 2020!");
        System.out.println();

        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter the day to solve (01-25, 0 to quit, 99 to solve all): ");

            final int day = scanner.nextInt();
            if (day == 0) {
                break;
            }

            if (day == 99) {
                for (int i = 1; i <= 25; i++) {
                    final Solution solution = Solution.getSolution(i);
                    System.out.printf("Day %02d Part 1: %s\n", i, solution.getPart1Solution());
                    System.out.printf("Day %02d Part 2: %s\n", i, solution.getPart2Solution());
                }

                break;
            }

            if (day > 0 && day < 26) {
                runSolution(day, scanner);
            }
        }

        System.out.println();
        System.out.println("Happy Holidays!");
        scanner.close();
    }

    private static void runSolution(int day, Scanner scanner) throws Exception {
        System.out.printf("Day %02d loaded.\n", day);
        final Solution solution = Solution.getSolution(day);

        while (true) {
            System.out.printf("Please enter the part of day %02d to solve (1 or 2, 0 to go back): ", day);

            Object answer = null;
            final int part = scanner.nextInt();
            switch (part) {
                case 0:
                    return;
                case 1:
                    answer = solution.getPart1Solution();
                    break;
                case 2:
                    answer = solution.getPart2Solution();
                    break;
            }

            if (answer != null) {
                System.out.printf("The solution for Day %02d Part %d is: %s\n", day, part, answer);
            }
        }
    }
}
