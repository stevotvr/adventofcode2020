package day12;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class day12 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day12/day12.txt"));
        final Instruction[] instructions = new Instruction[input.size()];
        for (int i = 0; i < instructions.length; i++) {
            final String line = input.get(i);
            instructions[i] = new Instruction(line.charAt(0), Integer.parseInt(line.substring(1)));
        }

        part1(instructions);
        part2(instructions);
    }

    private static void part1(Instruction[] instructions) {
        final Boat boat = new Boat();
        for (Instruction instruction : instructions) {
            boat.execute(instruction);
        }

        System.out.println(Math.abs(boat.x) + Math.abs(boat.y));
    }

    private static void part2(Instruction[] instructions) {
        final Boat boat = new Boat();
        for (Instruction instruction : instructions) {
            boat.executeWaypoint(instruction);
        }

        System.out.println(Math.abs(boat.x) + Math.abs(boat.y));
    }

    private static class Instruction {
        public final char moveType;
        public final int distance;

        public Instruction(char moveType, int distance) {
            this.moveType = moveType;
            this.distance = distance;
        }
    }

    private static class Boat {
        private static final int[][] directions = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        private int x;
        private int y;
        private int direction = 0;
        private int dx = 10;
        private int dy = -1;

        public void execute(Instruction instruction) {
            switch (instruction.moveType) {
                case 'N':
                    this.y -= instruction.distance;
                    break;
                case 'S':
                    this.y += instruction.distance;
                    break;
                case 'E':
                    this.x += instruction.distance;
                    break;
                case 'W':
                    this.x -= instruction.distance;
                    break;
                case 'L':
                    this.direction = Math.floorMod(this.direction - instruction.distance / 90, 4);
                    break;
                case 'R':
                    this.direction = (this.direction + instruction.distance / 90) % 4;
                    break;
                case 'F':
                    this.x += directions[this.direction][0] * instruction.distance;
                    this.y += directions[this.direction][1] * instruction.distance;
                    break;
            }
        }

        public void executeWaypoint(Instruction instruction) {
            switch (instruction.moveType) {
                case 'N':
                    this.dy -= instruction.distance;
                    break;
                case 'S':
                    this.dy += instruction.distance;
                    break;
                case 'E':
                    this.dx += instruction.distance;
                    break;
                case 'W':
                    this.dx -= instruction.distance;
                    break;
                case 'L':
                    rotateWaypoint(-instruction.distance / 90);
                    break;
                case 'R':
                    rotateWaypoint(instruction.distance / 90);
                    break;
                case 'F':
                    this.x += this.dx * instruction.distance;
                    this.y += this.dy * instruction.distance;
                    break;
            }
        }

        private void rotateWaypoint(int turns) {
            final int cx = this.dx;
            final int cy = this.dy;

            switch (turns) {
                case 1:
                case -3:
                    this.dx = -cy;
                    this.dy = cx;
                    break;
                case 2:
                case -2:
                    this.dx = -cx;
                    this.dy = -cy;
                    break;
                case 3:
                case -1:
                    this.dx = cy;
                    this.dy = -cx;
                    break;
            }
        }
    }
}
