package com.grahamdaw;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class DepthMeasurements {

    private final List<Integer> measurements = new ArrayList<>();

    static boolean isBigger(Integer prev, Integer next) {
        return prev < next;
    }

    public void addMeasurement(Integer measurement) {
        measurements.add(measurement);
    }

    public boolean currentlyGoingDeeper() {
        Integer previous = measurements.get(max(0, measurements.size() - 2));
        Integer current = measurements.get(measurements.size() - 1);
        return isBigger(previous, current);

    }
}
