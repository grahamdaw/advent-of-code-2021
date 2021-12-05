package com.grahamdaw;

import java.util.ArrayList;
import java.util.List;

public class Depth {

    private final List<Integer> measurements = new ArrayList<>();
    private int timesDeeper = 0;

    static boolean isDeeper(Integer prev, Integer next) {
        return prev < next;
    }

    public void addMeasurement(Integer measurement) {
        measurements.add(measurement);
        if (haveWeGoneDeeper()) {
            timesDeeper++;
        }
    }

    public int getTimesDeeper() {
        return timesDeeper;
    }

    private boolean haveWeGoneDeeper() {
        // Non-windowed checks (day 1 part 1)
        // Integer previous = measurements.get(max(0, measurements.size() - 2));
        // Integer current = measurements.get(measurements.size() - 1);

        // Only start checks if we have enough data points to compare
        if (measurements.size() < 4) {
            return false;
        }
        // There is enough data in the array to start checking measurements
        List<Integer> previousWindow = measurements.subList(
                measurements.size() - 4,
                measurements.size() - 1
        );
        List<Integer> currentWindow = measurements.subList(
                measurements.size() - 3,
                measurements.size()
        );

        Integer previous = previousWindow.stream().mapToInt(Integer::intValue).sum();
        Integer current = currentWindow.stream().mapToInt(Integer::intValue).sum();

        // System.out.println("Checking " + previous + " vs " + current);

        // If needed can make this more complex to measure other types of changes (e.g. shallower, no-change)
        return isDeeper(previous, current);

    }
}
