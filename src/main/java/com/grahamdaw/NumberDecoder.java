package com.grahamdaw;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.grahamdaw.Utils.integerListToString;
import static com.grahamdaw.Utils.sortStringChars;

public class NumberDecoder {

    public static Integer decodeNumbers(String keys, String encoded) {
        List<String> codeSheet = getCodeSheet(Arrays.asList(keys.split("\\s+")));
        System.out.println("codeSheet " + codeSheet);
        List<String> encodedNumbers = Arrays.asList(encoded.split("\\s+"));
        System.out.println("encodedNumbers " + encodedNumbers);
        List<Integer> decodedNumbers = new ArrayList<>();
        for(String encodedNum: encodedNumbers){
            Integer num = lookupIntegerFromCodeSheet(encodedNum, codeSheet);
            decodedNumbers.add(num);
        }
        Integer parsed = Integer.parseInt(integerListToString(decodedNumbers));
        System.out.println("decodedNumbers " + decodedNumbers + " parsed to " + parsed);
        return parsed;
    }

    private static List<String> getCodeSheet(List<String> keys) {
        List<String> codeSheet = new ArrayList<>(Collections.nCopies(10, null));

        // First figure out the simple keys
        for(String key: keys) {
            if (key.length() == 2) {
                codeSheet.set(1, sortStringChars(key));
            }
            if (key.length() == 3) {
                codeSheet.set(7, sortStringChars(key));
            }
            if (key.length() == 4) {
                codeSheet.set(4, sortStringChars(key));
            }
            if (key.length() == 7) {
                codeSheet.set(8, sortStringChars(key));
            }
        }

        // Next the numbers with 6 lines
        // Try to overlay a known number on the unkown number
        for(String key: keys) {
            if (key.length() == 6) {
                // 6/9/0
                if (!canOverlayCharactersOnBase(codeSheet.get(1), key)) {
                    // 1 cannot be overlayed on 6
                    codeSheet.set(6, sortStringChars(key));
                } else if (!canOverlayCharactersOnBase(codeSheet.get(4), key)) {
                    // 4 cannot be overlayed on 0
                    codeSheet.set(0, sortStringChars(key));
                } else {
                    // last is 9
                    codeSheet.set(9, sortStringChars(key));
                }
            }
        }

        // Now the characters with 5 lines
        // Try to overlay the unknown number on a known number
        for(String key: keys) {
            if (key.length() == 5) {
                // 2/3/5
                if (!canOverlayCharactersOnBase(key, codeSheet.get(9))) {
                    // 2 cannot be overlayed on 9
                    codeSheet.set(2, sortStringChars(key));
                } else if (!canOverlayCharactersOnBase(key, codeSheet.get(6))) {
                    // 3 cannot be overlayed on 6
                    codeSheet.set(3, sortStringChars(key));
                } else {
                    // last is 5
                    codeSheet.set(5, sortStringChars(key));
                }

            }
        }
        return codeSheet;
    }

    private static Integer lookupIntegerFromCodeSheet(String encoded, List<String> codeSheet) {
        String key = sortStringChars(encoded);
        for(int i = 0; i < codeSheet.size(); i++) {
            if (codeSheet.get(i).equals(key)) {
                return i;
            }
        }
        return null;
    }

    private static boolean canOverlayCharactersOnBase(String overlay, String base) {
        // Split overlay string to characters
        char[] chars = overlay.toCharArray();
        // Check the comparison characters - once one is missing return false
        for (char c: chars) {
            if (base.indexOf(c) < 0) {
                return false;
            };
        }
        // If we've made it this far then all characters are present
        return true;
    }
}
