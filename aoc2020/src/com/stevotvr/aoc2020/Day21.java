package com.stevotvr.aoc2020;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day21 extends Solution {
    @Override
    public Object getPart1Solution() {
        final InputData inputData = getInput();

        final HashSet<String> inertSet = getInertIngredients(inputData.ingredients, inputData.allergens);

        long total = 0;
        for (String ingredient : inertSet) {
            for (HashSet<String> set : inputData.ingredients) {
                total += set.contains(ingredient) ? 1 : 0;
            }
        }

        return total;
    }

    @Override
    public Object getPart2Solution() {
        final InputData inputData = getInput();

        final HashSet<String> inertSet = getInertIngredients(inputData.ingredients, inputData.allergens);

        final HashMap<String, String> allergenMap = new HashMap<>();
        final HashMap<String, Set<String>> candidateMap = new HashMap<>();
        for (int i = 0; i < inputData.ingredients.size(); i++) {
            final Set<String> v = inputData.ingredients.get(i).stream().filter(a -> !inertSet.contains(a)).collect(Collectors.toSet());
            for (String k : inputData.allergens.get(i)) {
                if (!candidateMap.containsKey(k)) {
                    candidateMap.put(k, new HashSet<>(v));
                    allergenMap.put(k, null);
                } else {
                    candidateMap.get(k).retainAll(v);
                }
            }
        }

        while (allergenMap.containsValue(null)) {
            final Entry<String, Set<String>> candidate = candidateMap.entrySet().stream().filter(a -> a.getValue().size() == 1).findFirst().get();
            final String ingredient = candidate.getValue().stream().findFirst().get();
            allergenMap.put(candidate.getKey(), ingredient);
            candidateMap.values().stream().forEach(a -> a.remove(ingredient));
        }

        return allergenMap.entrySet().stream().sorted(Entry.comparingByKey()).map(Entry::getValue).collect(Collectors.joining(","));
    }

    private InputData getInput() {
        final List<String> input = getInputLines();
        final List<HashSet<String>> ingredients = new ArrayList<>();
        final List<HashSet<String>> allergens = new ArrayList<>();
        for (String line : input) {
            final String[] parts = line.split(" \\(contains ");
            HashSet<String> set = new HashSet<>();
            for (String s : parts[0].split(" ")) {
                set.add(s);
            }

            ingredients.add(set);

            set = new HashSet<>();
            for (String s : parts[1].substring(0, parts[1].length() - 1).split(", ")) {
                set.add(s);
            }

            allergens.add(set);
        }

        return new InputData(ingredients, allergens);
    }

    private static class InputData {
        public final List<HashSet<String>> ingredients;
        public final List<HashSet<String>> allergens;

        public InputData(List<HashSet<String>> ingredients, List<HashSet<String>> allergens) {
            this.ingredients = ingredients;
            this.allergens = allergens;
        }
    }

    private static HashSet<String> getInertIngredients(List<HashSet<String>> ingredients, List<HashSet<String>> allergens) {
        final HashSet<String> ingredientsSet = new HashSet<>();
        ingredients.stream().forEach(a -> ingredientsSet.addAll(a));

        final HashSet<String> allergenSet = new HashSet<>();
        allergens.stream().forEach(a -> allergenSet.addAll(a));

        final Deque<String> queue = new ArrayDeque<>(allergenSet);
        while (!queue.isEmpty()) {
            final String current = queue.removeLast();
            HashSet<String> possible = null;
            for (int i = 0; i < ingredients.size(); i++) {
                if (!allergens.get(i).contains(current)) {
                    continue;
                }

                if (possible == null) {
                    possible = new HashSet<>(ingredients.get(i));
                } else {
                    final HashSet<String> ingredientSubset = ingredients.get(i);
                    possible.removeIf(a -> !ingredientSubset.contains(a));
                }
            }

            if (possible != null && possible.size() > 0) {
                ingredientsSet.removeAll(possible);
            }
        }

        return ingredientsSet;
    }
}
