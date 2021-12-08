package com.grahamdaw;

import java.util.ArrayList;
import java.util.List;

import static com.grahamdaw.Utils.calcSumOfTriangleNumberBecauseIDontKnowWhatItsCalled;

public class CrabSubOptimizer {
    private List<Integer> subPositions = new ArrayList<>();
    private Integer maxPostion = null;
    private Integer minPostion = null;

    public CrabSubOptimizer(List<Integer> initialPositions) {
        this.subPositions = initialPositions;
        for(Integer pos: initialPositions) {
            if (minPostion == null || pos.intValue() < minPostion.intValue()) {
                minPostion = pos;
            }
            if (maxPostion == null || pos.intValue() > maxPostion.intValue()) {
                maxPostion = pos;
            }
        }
        System.out.println("Number of crab subs " + initialPositions.size());
        System.out.println("Min Position: " + minPostion + " | Max Position: " + maxPostion);
    }

    public Integer moveBruteForcePart1() {
        Integer range = maxPostion - minPostion;
        Double min = Double.POSITIVE_INFINITY;
        Integer centerValue = null;
        for(int i = 0; i <= range; i++) {
            // For this number, check total if we moved all points to this.
            // If answer is smaller than others, keep
            Integer total = 0;
            for(Integer pos: subPositions) {
                total += Math.abs(i - pos.intValue());
            }
            if (total.intValue() < min.intValue()) {
                min = total.doubleValue();
                centerValue = i;
            }
        }
        System.out.println("Center value " + centerValue + " gave a min cost of " + min);
        return min.intValue();
    }

    public Integer moveBruteForcePart2() {
        Integer range = maxPostion - minPostion;
        Double min = Double.POSITIVE_INFINITY;
        Integer centerValue = null;
        for(int i = 0; i <= range; i++) {
            // For this number, check total if we moved all points to this.
            // If answer is smaller than others, keep
            Integer total = 0;
            for(Integer pos: subPositions) {
                // For part 2, increased movement becomes more expensive.
                // Do triangle number things #maths
                Integer diff = Math.abs(i - pos.intValue());
                total += calcSumOfTriangleNumberBecauseIDontKnowWhatItsCalled(diff);
            }
            if (total.intValue() < min.intValue()) {
                min = total.doubleValue();
                centerValue = i;
            }
        }
        System.out.println("Center value " + centerValue + " gave a min cost of " + min);
        return min.intValue();
    }

    /*
    Calc range
    For each calc the distance from
     */
}
