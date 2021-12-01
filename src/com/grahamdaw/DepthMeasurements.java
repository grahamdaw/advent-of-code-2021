package com.grahamdaw;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class DepthMeasurements {

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
        Integer previous = measurements.get(max(0, measurements.size() - 2));
        Integer current = measurements.get(measurements.size() - 1);
        return isDeeper(previous, current);

    }
}
