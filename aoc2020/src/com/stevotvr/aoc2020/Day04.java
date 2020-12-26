package com.stevotvr.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Day04 extends Solution {
    private static final List<String> REQUIRED = Arrays.asList(new String[]{ "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" });
    private static final List<String> EYE_COLORS = Arrays.asList(new String[]{ "amb", "blu", "brn", "gry", "grn", "hzl", "oth" });

    @Override
    public Object getPart1Solution() {
        final ArrayList<HashMap<String, String>> passports = getInput();

        int valid = 0;
        for (HashMap<String, String> passport : passports) {
            valid += passport.keySet().containsAll(REQUIRED) ? 1 : 0;
        }

        return valid;
    }

    @Override
    public Object getPart2Solution() {
        final ArrayList<HashMap<String, String>> passports = getInput();

        int valid = 0;
        for (HashMap<String, String> passport : passports) {
            if (!passport.keySet().containsAll(REQUIRED)) {
                continue;
            }

            boolean isValid = true;
            for (Entry<String, String> field : passport.entrySet()) {
                try {
                    switch (field.getKey()) {
                        case "byr":
                            final int byr = Integer.parseInt(field.getValue());
                            if (byr < 1920 || byr > 2002) {
                                isValid = false;
                            }

                            break;
                        case "iyr":
                            final int iyr = Integer.parseInt(field.getValue());
                            if (iyr < 2010 || iyr > 2020) {
                                isValid = false;
                            }

                            break;
                        case "eyr":
                            final int eyr = Integer.parseInt(field.getValue());
                            if (eyr < 2020 || eyr > 2030) {
                                isValid = false;
                            }

                            break;
                        case "hgt":
                            final String hgt = field.getValue();
                            if (hgt.length() < 4) {
                                isValid = false;
                                break;
                            }

                            final int hgtValue = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
                            isValid = false;
                            if (hgt.endsWith("cm") && hgtValue >= 150 && hgtValue <= 193) {
                                isValid = true;
                                break;
                            }

                            if (hgt.endsWith("in") && hgtValue >= 59 && hgtValue <= 76) {
                                isValid = true;
                                break;
                            }

                            break;
                        case "hcl":
                            if (!field.getValue().matches("^#[0-9a-f]{6}$")) {
                                isValid = false;
                            }

                            break;
                        case "ecl":
                            if (!EYE_COLORS.contains(field.getValue())) {
                                isValid = false;
                            }

                            break;
                        case "pid":
                            if (!field.getValue().matches("^[0-9]{9}$")) {
                                isValid = false;
                            }

                            break;
                    }
                }
                catch (Exception e) {
                    break;
                }

                if (!isValid) {
                    break;
                }
            }

            valid += isValid ? 1 : 0;
        }

        return valid;
    }

    private ArrayList<HashMap<String, String>> getInput() {
        final String input = getInpuString();
        final String[] blocks = input.split("\n\n");
        final ArrayList<HashMap<String, String>> passports = new ArrayList<>();
        for (String block : blocks) {
            final String[] fields = block.split("[ \n]");
            final HashMap<String, String> kv = new HashMap<>();
            for (String field : fields) {
                final String[] parts = field.split(":");
                kv.put(parts[0], parts[1]);
            }

            passports.add(kv);
        }

        return passports;
    }
}
