package com.grahamdaw;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class NavSubsysParser {
    Map<String, String> corrupted = new HashMap<>();
    Map<String, List<String>> autocompleted = new HashMap<>();

    public void addDataRow(String row) {
        try {
            var illTag = findIllegalTag(row);
            if (illTag != null) {
                corrupted.put(row, illTag);
            } else {
                var autoCompleted = completeRow(row);
                if (autoCompleted != null) {
                    autocompleted.put(row, autoCompleted);
                }
            }
            // Else do nothing with the line
        } catch (Exception e) {
            System.err.println("Could not parse: " + row);
        }
    }

    public Integer syntaxErrorScore() {
        System.out.println("Found " + corrupted.size() + " illegal rows");
        Integer score = 0;
        for (Map.Entry<String, String> entry : corrupted.entrySet()) {
            score += getIllegalScore(entry.getValue());
        }
        return score;
    }

    public BigInteger autocompleteScore() {
        System.out.println("Autocompleted " + autocompleted.size() + " rows");
        List<BigInteger> scores = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : autocompleted.entrySet()) {
            BigInteger lineScore = new BigInteger("0");
            for(String extra: entry.getValue()) {

                lineScore = (lineScore.multiply(new BigInteger("5"))).add(getAutocompleteScore(extra));
            }
            scores.add(lineScore);
        }
        Collections.sort(scores);
        System.out.println("Line scores " + scores);
        // Get middle value
        return scores.get(Math.round(scores.size()/2));
    }

    private String findIllegalTag(String str) throws Exception {
        List<String> expectedClosingTags = new ArrayList<>();
        for(char c: str.toCharArray()) {
            var tag = Character.toString(c);
            if (isOpeningTag(tag)) {
                expectedClosingTags.add(getClosingTag(tag));
            } else if (isClosingTag(tag)){
                // Check it matches the expected closing tag
                // If so, remove it from the stack, if not return illegal character.
                var expectedTag = expectedClosingTags.get(expectedClosingTags.size() - 1);
                if (tag.equals(expectedTag)) {
                    expectedClosingTags.remove(expectedClosingTags.size() - 1);
                } else {
                    // Illegal char
                    return tag;
                }
            } else {
                throw new Exception("Unknown character '" + tag + "'");
            }
        }
        return null;
    }

    private boolean isOpeningTag(String tag) {
        return Set.of("(", "[", "{", "<").contains(tag);
    }

    private boolean isClosingTag(String tag) {
        return Set.of(")", "]", "}", ">").contains(tag);
    }

    private String getClosingTag(String open) {
        if (open.equals("(")) {
            return ")";
        } else if (open.equals("[")) {
            return "]";
        } else if (open.equals("{")) {
            return "}";
        } else if (open.equals("<")) {
            return ">";
        } else {
            return null;
        }
    }

    private Integer getIllegalScore(String tag) {
        if (tag.equals(")")) {
            return 3;
        } else if (tag.equals("]")) {
            return 57;
        } else if (tag.equals("}")) {
            return 1197;
        } else if (tag  .equals(">")) {
            return 25137;
        } else {
            return 0;
        }
    }

    private List<String> completeRow(String row) throws Exception {
        List<String> expectedClosingTags = new ArrayList<>();
        for(char c: row.toCharArray()) {
            var tag = Character.toString(c);
            if (isOpeningTag(tag)) {
                expectedClosingTags.add(getClosingTag(tag));
            } else if (isClosingTag(tag)){
                // Check it matches the expected closing tag
                // If so, remove it from the stack, if not return illegal character.
                var expectedTag = expectedClosingTags.get(expectedClosingTags.size() - 1);
                if (tag.equals(expectedTag)) {
                    expectedClosingTags.remove(expectedClosingTags.size() - 1);
                }
            } else {
                throw new Exception("Unknown character '" + tag + "'");
            }
        }

        // // Make sure to reverse sort the tags for scoring
        Collections.reverse(expectedClosingTags);
        return expectedClosingTags;
    }

    private BigInteger getAutocompleteScore(String tag) {
        String strScore = "0";
        if (tag.equals(")")) {
            strScore = "1";
        } else if (tag.equals("]")) {
            strScore = "2";
        } else if (tag.equals("}")) {
            strScore = "3";
        } else if (tag  .equals(">")) {
            strScore = "4";
        }
        return new BigInteger(strScore);
    }

}


