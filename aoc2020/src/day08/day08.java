package day08;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

public class day08 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day08/day08.txt"));
        final Instruction[] program = new Instruction[input.size()];
        for (int i = 0; i < input.size(); i++) {
            final String[] parts = input.get(i).split(" ");
            final int value = Integer.parseInt(parts[1]);
            switch (parts[0]) {
                case "acc":
                    program[i] = new Acc(value);
                    break;
                case "jmp":
                    program[i] = new Jmp(value);
                    break;
                default:
                    program[i] = new Nop(value);
            }
        }

        final Computer computer = new Computer(program);

        part1(computer);
        part2(computer);
    }

    private static void part1(Computer computer) {
        final HashSet<Instruction> history = new HashSet<>();
        while (computer.execute()) {
            final Instruction instruction = computer.program[computer.position];
            if (history.contains(instruction)) {
                System.out.println(computer.accumulator);
                return;
            }

            history.add(instruction);
        }
    }

    private static void part2(Computer computer) {
        for (int i = 0; i < computer.program.length; i++) {
            final Instruction original = computer.program[i];
            if (original instanceof Jmp) {
                computer.program[i] = new Nop(0);
            } else if (original instanceof Nop) {
                computer.program[i] = new Jmp(original.value);
            } else {
                continue;
            }

            final HashSet<Instruction> history = new HashSet<>();
            while (computer.execute()) {
                if (computer.position >= computer.program.length) {
                    System.out.println(computer.accumulator);
                    return;
                }

                final Instruction instruction = computer.program[computer.position];
                if (history.contains(instruction)) {
                    break;
                }
    
                history.add(instruction);
            }

            computer.program[i] = original;
            computer.reset();
        }
    }

    private static class Computer {
        private int position = 0;

        private int accumulator = 0;

        private final Instruction[] program;

        public Computer(Instruction[] program) {
            this.program = program.clone();
        }

        public boolean execute() {
            if (this.position < 0 || this.position >= this.program.length) {
                return false;
            }

            this.program[this.position].exec(this);

            return true;
        }

        public void reset() {
            this.position = 0;
            this.accumulator = 0;
        }
    }

    private static abstract class Instruction {
        public final int value;

        public Instruction(int value) {
            this.value = value;
        }

        public abstract void exec(Computer computer);
    }

    private static class Acc extends Instruction {
        public Acc(int value) {
            super(value);
        }

        @Override
        public void exec(Computer computer) {
            computer.accumulator += this.value;
            computer.position++;
        }
    }

    private static class Jmp extends Instruction {
        public Jmp(int value) {
            super(value);
        }
        
        @Override
        public void exec(Computer computer) {
            computer.position += this.value;
        }
    }

    private static class Nop extends Instruction {
        public Nop(int value) {
            super(value);
        }
        
        @Override
        public void exec(Computer computer) {
            computer.position++;
        }
    }
}
