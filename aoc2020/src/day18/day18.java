package day18;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class day18 {

    public static void main(String[] args) throws Exception {
        final List<String> input = Files.readAllLines(Path.of("aoc2020/src/day18/day18.txt"));
        final List<Symbol[]> expressions = new ArrayList<>();
        for (String line : input) {
            line = line.replace(" ", "");
            final Symbol[] expression = new Symbol[line.length()];
            for (int i = 0; i < expression.length; i++) {
                expression[i] = Symbol.interpret(line.charAt(i));
            }

            expressions.add(expression);
        }

        part1(expressions);
        part2(expressions);
    }

    private static void part1(List<Symbol[]> expressions) {
        long total = 0;
        for (Symbol[] expression : expressions) {
            total += evaluate(expression, false);
        }

        System.out.println(total);
    }

    private static void part2(List<Symbol[]> expressions) {
        long total = 0;
        for (Symbol[] expression : expressions) {
            total += evaluate(expression, true);
        }

        System.out.println(total);
    }

    private static long evaluate(Symbol[] expression, boolean advanced) {
        return advanced ? evaluateAdvanced(expression, -1)[0] : evaluate(expression, -1)[0];
    }

    private static long[] evaluate(Symbol[] expression, int start) {
        long result = 0;
        Operator op = null;
        for (int i = start + 1; i < expression.length; i++) {
            if (expression[i] instanceof Close) {
                break;
            }

            if (expression[i] instanceof Open) {
                final long[] sub = evaluate(expression, i);
                result = op == null ? sub[0] : op.evaluate(result, sub[0]);
                i = (int)sub[1];

                continue;
            }

            if (expression[i] instanceof Operator) {
                op = (Operator)expression[i];
            } else {
                result = op == null ? ((Digit)expression[i]).value : op.evaluate(result, ((Digit)expression[i]).value);
            }
        }

        return new long[] { result, expression.length };
    }

    private static long[] evaluateAdvanced(Symbol[] expression, int start) {
        Operator op = null;
        final Deque<Long> addPass = new ArrayDeque<>();
        int i = start + 1;
        for (; i < expression.length; i++) {
            if (expression[i] instanceof Close) {
                break;
            }

            if (expression[i] instanceof Open) {
                final long[] sub = evaluateAdvanced(expression, i);

                addPass.add(op instanceof Add ? addPass.removeLast() + sub[0] : sub[0]);
                i = (int)sub[1];

                continue;
            }

            if (expression[i] instanceof Operator) {
                op = (Operator)expression[i];
            } else if (op instanceof Add) {
                addPass.add(addPass.removeLast() + ((Digit)expression[i]).value);
            } else {
                addPass.add(((Digit)expression[i]).value);
            }
        }

        return new long[] { addPass.stream().reduce((a, b) -> a * b).get(), i };
    }

    private static class Symbol {
        public static Symbol interpret(char symbol) {
            switch (symbol) {
                case '+':
                    return new Add();
                case '*':
                    return new Multiply();
                case '(':
                    return new Open();
                case ')':
                    return new Close();
            }

            return new Digit(symbol - '0');
        }
    }

    private static class Digit extends Symbol {
        public final long value;

        public Digit(long value) {
            this.value = value;
        }
    }

    private static abstract class Operator extends Symbol {
        public abstract long evaluate(long a, Long b);
    }

    private static class Add extends Operator {
        @Override
        public long evaluate(long a, Long b) {
            return a + b;
        }
    }

    private static class Multiply extends Operator {
        @Override
        public long evaluate(long a, Long b) {
            return a * b;
        }
    }

    private static class Open extends Symbol { }

    private static class Close extends Symbol { }
}
