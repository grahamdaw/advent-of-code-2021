package com.grahamdaw;

import java.util.*;
import java.util.stream.Collectors;

public class Diagnostics {

    private final List<String> readings = new ArrayList<>();

    public void addReading(String reading) {
        readings.add(reading);
    }

    public int calculatePowerConsumption() {
        List<Map<String, Integer>> columns = new ArrayList<>();
        for (String reading : readings) {
            // Go through each entry
            List<String> splitReading = Arrays.asList(reading.split(""));
            for (int i = 0; i < splitReading.size(); i++) {
                // Now how pos in byte string
                Map<String, Integer> column;
                try {
                    column = columns.get(i);
                } catch (IndexOutOfBoundsException e) {
                    columns.add(i, new HashMap<>());
                    column = columns.get(i);
                }
                // Check current value & increment correct value
                String bit = splitReading.get(i);
                if (column.get(bit) != null) {
                    column.put(bit, column.get(bit) + 1);
                } else {
                    column.put(bit, 1);
                }
            }
        }
        List<String> gammaBytes  = new ArrayList<>();
        List<String> epsilonBytes  = new ArrayList<>();
        // Now build the output
        for (Map<String, Integer> column : columns) {
            // Figure out least/most common values
            int offVal = column.get("0");
            int onVal = column.get("1");
            if (offVal > onVal) {
                gammaBytes.add("0");
                epsilonBytes.add("1");
            } else {
                gammaBytes.add("1");
                epsilonBytes.add("0");
            }
        }

        int gamma = Integer.parseInt(String.join("", gammaBytes), 2);
        int epsilon = Integer.parseInt(String.join("", epsilonBytes), 2);
        int powerConsumption = gamma * epsilon;
        System.out.println("Power consumption " + gamma + " * " + epsilon + " = " + powerConsumption);
        return powerConsumption;
    }

    public int calculateLifeSupport() {
        int o2 = getOxygenGeneratorRating();
        int co2 = getCO2ScrubberRating();
        int lifeSupport = o2 * co2;
        System.out.println("Life support " + o2 + " * " + co2 + " = " + lifeSupport);
        return lifeSupport;
    }

    private Map<String, List<String>> groupListByCharAtIndex(List<String> list, int index) {
        Map<String, List<String>> groupedStrings = new HashMap<>();

        for (String value : list) {
            String character = String.valueOf(value.charAt(index));
            List<String> group = groupedStrings.get(character);
            if (group == null) {
                groupedStrings.put(character, new ArrayList<>());
                group = groupedStrings.get(character);
            }
            group.add(value);
        }
        return groupedStrings;
    }

    private List<String> getLargestGroupAtIndex(List<String> list, int index) {
        Map<String, List<String>> groups = groupListByCharAtIndex(list, index);
        // System.out.println("LRG " + index + " | " + groups);
        List<String> offGroup = groups.get("0");
        List<String> onGroup = groups.get("1");

        if (onGroup.size() > offGroup.size()) {
            return onGroup;
        } else if (onGroup.size() < offGroup.size()) {
            return offGroup;
        } else {
            return onGroup;
        }
    }

    private List<String> getSmallestGroupAtIndex(List<String> list, int index) {
        Map<String, List<String>> groups = groupListByCharAtIndex(list, index);
        List<String> offGroup = groups.get("0");
        List<String> onGroup = groups.get("1");
        // System.out.println("SML " + index + " | " + groups);
        if (onGroup.size() > offGroup.size()) {
            return offGroup;
        } else if (onGroup.size() < offGroup.size()) {
            return onGroup;
        } else {
            return offGroup;
        }
    }

    private Integer getOxygenGeneratorRating() {
        // This should be done better, but quick fix to get number of bytes in each string
        int size = Arrays.asList(readings.get(0).split("")).size();
        List<String> result = new ArrayList(readings);
        for (int i = 0; i < size; i++) {
            // If one result left, then just use that
            if( result.size() == 1 ) {
                break;
            }
            result = getLargestGroupAtIndex(result, i);
        }
        return Integer.parseInt(result.get(0), 2);
    }

    private int getCO2ScrubberRating() {
        // This should be done better, but quick fix to get number of bytes in each string
        int size = Arrays.asList(readings.get(0).split("")).size();
        List<String> result = new ArrayList(readings);
        for (int i = 0; i < size; i++) {
            // If one result left, then just use that
            if( result.size() == 1 ) {
                break;
            }
            result = getSmallestGroupAtIndex(result, i);
        }
        return Integer.parseInt(result.get(0), 2);
    }
}
