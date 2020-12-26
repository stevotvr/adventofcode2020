package com.stevotvr.aoc2020;

import java.util.List;

public class Day25 extends Solution {
    @Override
    public Object getPart1Solution() {
        final InputData inputData = getInput();

        long value = 1;
        long loop = 0;
        while (value != inputData.doorkey && value != inputData.cardkey) {
            value *= 7;
            value %= 20201227;
            loop++;
        }

        final long subj = value == inputData.doorkey ? inputData.cardkey : inputData.doorkey;

        value = 1;
        for (long i = 0; i < loop; i++) {
            value *= subj;
            value %= 20201227;
        }

        return value;
    }

    @Override
    public Object getPart2Solution() {
        return "*";
    }

    private InputData getInput() {
        final List<String> input = getInputLines();
        return new InputData(Long.parseLong(input.get(0)), Long.parseLong(input.get(1)));
    }

    private static class InputData {
        public final long doorkey;
        public final long cardkey;

        public InputData(long doorkey, long cardkey) {
            this.doorkey = doorkey;
            this.cardkey = cardkey;
        }
    }
}
