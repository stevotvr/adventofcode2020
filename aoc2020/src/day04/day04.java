package day04;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class day04 {
    private static List<String> required = Arrays.asList(new String[]{ "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" });

    private static List<String> eyeColors = Arrays.asList(new String[]{ "amb", "blu", "brn", "gry", "grn", "hzl", "oth" });

    public static void main(String[] args) throws Exception {
        final String input = Files.readString(Path.of("aoc2020/src/day04/day04.txt"));
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

        part1(passports);
        part2(passports);
    }

    private static void part1(List<HashMap<String, String>> passports) {
        int valid = 0;
        for (HashMap<String, String> passport : passports) {
            valid += passport.keySet().containsAll(required) ? 1 : 0;
        }

        System.out.println(valid);
    }

    private static void part2(List<HashMap<String, String>> passports) {
        int valid = 0;
        for (HashMap<String, String> passport : passports) {
            if (!passport.keySet().containsAll(required)) {
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
                            if (!eyeColors.contains(field.getValue())) {
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

        System.out.println(valid);
    }
}
