package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Solution {
    @Override
    public Object getPart1Solution() {
        final InputData inputData = getInput();

        long depart = inputData.minimum;
        while (true) {
            for (long id : inputData.ids) {
                if (id < 0) {
                    continue;
                }

                if (depart % id == 0) {
                    return id * (depart - inputData.minimum);
                }
            }

            depart++;
        }
    }

    @Override
    public Object getPart2Solution() {
        final InputData inputData = getInput();

        long lcm = -1, time = -1;
        int index = 0;
        while (true) {
            final long id = inputData.ids.get(index);
            if (id == -1) {
                index++;
                continue;
            }

            if (lcm == -1) {
                lcm = id;
                time = id - index;
                index++;
                continue;
            }

            if ((time + index) % id == 0) {
                if (++index >= inputData.ids.size()) {
                    return time;
                }

                lcm *= id;
                continue;
            }

            time += lcm;
        }
    }

    private InputData getInput() {
        final List<String> input = getInputLines();
        final long minimum = Long.parseLong(input.get(0));
        final List<Long> ids = new ArrayList<>();
        for (String id : input.get(1).split(",")) {
            ids.add(id.equals("x") ? -1 : Long.parseLong(id));
        }

        return new InputData(ids, minimum);
    }

    private static class InputData {
        public final List<Long> ids;
        public final long minimum;

        public InputData(List<Long> ids, long minimum) {
            this.ids = ids;
            this.minimum = minimum;
        }
    }
}
