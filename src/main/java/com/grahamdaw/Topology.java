package com.grahamdaw;

import java.net.Inet4Address;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Topology {

    // Need to use something better?
    private List<List<Integer>> topology = new ArrayList<>();
    private Integer xMax = 0;
    private Integer yMax = 0;

    public Topology() {
        // Not doing nuffin'
    }

    public void printTopology() {
        System.out.println("Chart -----------");
        for (List<Integer> row: topology) {
            System.out.println(row.stream().map((i) -> Integer.toString(i)).collect(Collectors.joining(" ")));
        }
        System.out.println("-----------------");
    }

    public static List<Integer> stringToCoords(String raw) {
        return Arrays.asList(raw.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public void overlayLine(List<Integer> start, List<Integer> end) {
        // Get coords of line
        List<List<Integer>> points = getLinePoints(start, end);

        // Check if the table is larger - extend matrix
        Integer xMax = max(start.get(0), end.get(0));
        Integer yMax = max(start.get(1), end.get(1));
        setTopologyChartSize(xMax, yMax);

        // Apply coords to current matrix
        addPoints(points, 1);

    }

    public Integer getPeaks() {
        Integer peaks = 0;
        for (List<Integer> row: topology) {
            for (Integer height: row) {
                if (height.intValue() >= 2) {
                    peaks++;
                }
            }
        }
        return peaks;
    }

    private void addPoints(List<List<Integer>> points, Integer increment) {
        // For each point, find location and add value to chart
        for (List<Integer> point: points) {
            Integer y = point.get(1);
            Integer x = point.get(0);
            Integer newValue = topology.get(y).get(x) + increment;
            topology.get(y).set(x, newValue);
        }
    }

    private void setTopologyChartSize(Integer xMax,  Integer yMax) {
        // Add as many y rows as needed
        while (topology.size() <= yMax) {
            topology.add(new ArrayList<>());
        }
        // Now make sure the length of each List is increased to the max size
        for (List<Integer> row: topology) {
            while (row.size() <= xMax) {
                row.add(0);
            }
        }

        while (topology.size() < yMax) {
            topology.add(new ArrayList<>());
        }

    }

    private List<List<Integer>> getLinePoints(List<Integer> start, List<Integer> end) {
        // Determine points for the line to make on the chart
        List<List<Integer>> points = new ArrayList<>();

        Integer xStart = min(start.get(0), end.get(0));
        Integer xEnd = max(start.get(0), end.get(0));

        Integer yStart = min(start.get(1), end.get(1));
        Integer yEnd = max(start.get(1), end.get(1));

        if (yStart.intValue() == yEnd.intValue()) {
            // Vertical - get lowest x point to start with

            for (int i = xStart; i <= xEnd; i++) {
                points.add(new ArrayList<>(List.of(i, start.get(1))));
            }
        } else if (xStart.intValue() == xEnd.intValue()) {
            // Horizontal - get lowest y point to start with
            for (int i = yStart; i <= yEnd; i++) {
                points.add(new ArrayList<>(List.of(start.get(0), i)));
            }
        } else if ((xEnd - xStart) == (yEnd - yStart)) {
            // For part 2 of this problem, it only matters if a line is
            // a diagonal line @45 degrees (y = mx + b where m = +1 or -1)

            // To draw the points, start with the coordinate that has the
            // smallest value of y (so we always just increment y by +1)
            int yIncrement = 1;
            int xIncrement = 1;
            List<Integer> startingCoord = minYCoordinate(start, end);
            List<Integer> endingCoord = maxYCoordinate(start, end);

            // Invert the x inrement if the ending x point is less than the starting x point
            if (startingCoord.get(0).intValue() > endingCoord.get(0).intValue()) {
                xIncrement = -1;
            }

            int totalIncrements = endingCoord.get(1) - startingCoord.get(1);

            // Now lets make some points! These lines have the same number of increments for both
            // x & y coordinates, so just use the y value to track how many increments we should do
            for (int i = 0; i <= totalIncrements; i++) {
                Integer x = (i * xIncrement) + startingCoord.get(0);
                Integer y = (i * yIncrement) + startingCoord.get(1);
                points.add(new ArrayList<>(List.of(x, y)));
            }
        } else {
            // Some non-45 degree diagonal, which we don't care about for part 2
        }
        return points;
    }

    // not proud of this interface :/
    private static List<Integer> minYCoordinate(List<Integer> coords1, List<Integer> coords2) {
        if (coords1.get(1).intValue() > coords2.get(1).intValue()) {
            return coords2;
        } else {
            // return first value if equal
            return coords1;
        }
    }

    // Also not proud of this interface :/
    private static List<Integer> maxYCoordinate(List<Integer> coords1, List<Integer> coords2) {
        if (coords1.get(1).intValue() < coords2.get(1).intValue()) {
            return coords2;
        } else {
            // return first value if equal
            return coords1;
        }
    }

}
