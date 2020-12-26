package com.stevotvr.aoc2020;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Day18 extends Solution {
    @Override
    public Object getPart1Solution() {
        long total = 0;
        for (Symbol[] expression : getInput()) {
            total += evaluate(expression, false);
        }

        return total;
    }

    @Override
    public Object getPart2Solution() {
        long total = 0;
        for (Symbol[] expression : getInput()) {
            total += evaluate(expression, true);
        }

        return total;
    }

    private List<Symbol[]> getInput() {
        final List<String> input = getInputLines();
        final List<Symbol[]> expressions = new ArrayList<>();
        for (String line : input) {
            line = line.replace(" ", "");
            final Symbol[] expression = new Symbol[line.length()];
            for (int i = 0; i < expression.length; i++) {
                expression[i] = Symbol.interpret(line.charAt(i));
            }

            expressions.add(expression);
        }

        return expressions;
    }

    private static long evaluate(Symbol[] expression, boolean advanced) {
        return advanced ? evaluateAdvanced(expression, -1)[0] : evaluate(expression, -1)[0];
    }

    private static long[] evaluate(Symbol[] expression, int start) {
        long result = 0;
        Operator op = null;
        for (int i = start + 1; i < expression.length; i++) {
            if (expression[i] instanceof Close) {
                return new long[] { result, i };
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

    private static class Open extends Symbol {}

    private static class Close extends Symbol {}
}
