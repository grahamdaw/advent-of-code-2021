package com.grahamdaw;

import com.grahamdaw.utils.Matrix;

import java.util.*;
import java.util.stream.Collectors;

import static com.grahamdaw.Utils.csvStringToList;
import static com.grahamdaw.Utils.stringOfIntegersToList;

public class CaveMapper {
    private Matrix caveTopo = new Matrix();

    public void parseAndAddRow(String csv){
        caveTopo.addRow(stringOfIntegersToList(csv));
    }

    public void printMap(){
        caveTopo.print();
    }

    private Map<String, Integer> getLowPoints() {
        Map<String, Integer> lowPointRiskLevels = new HashMap<>();
        // Lets just do the dumb solution for now - they most likely is a more elegant solution
        // Keep getting points from 0,0 until null,null
        Integer row = 0;
        Integer column = 0;
        Integer riskLevel = 0;
        while(true) {
            var center = caveTopo.getCoords(column,row);
            if (center == null && column == 0) {
                break;
            }
            if (center == null && column > 0) {
                row++;
                column = 0;
                continue;
            }
            var top = caveTopo.getCoords(column, row-1);
            var right = caveTopo.getCoords(column+1, row);
            var bottom = caveTopo.getCoords(column, row+1);
            var left = caveTopo.getCoords(column-1, row);
            // Yes, I am not proud of this
            if ((top == null || center < top)
                    && (right == null || center < right)
                    && (bottom == null || center < bottom)
                    && (left == null || center < left)) {
                String coords = column + "," + row; // Lazy, but do this for now
                lowPointRiskLevels.put(coords, center + 1);
            }
            column++;
        }
        return lowPointRiskLevels;
    }

    public Integer calculateTotalRiskLevel() {
        Integer totalRiskLevel = 0;
        for (Integer level : getLowPoints().values()) {
            totalRiskLevel += level;
        }
        return totalRiskLevel;
    }

    public Integer findBasins() {
        // Not sure how to do this at the minute, so using this for testing
        List<String> checkedCoords = new ArrayList<>();
        List<Integer> basinSizes = new ArrayList<>();
        for (String coord : getLowPoints().keySet()) {
            // TODO store this variable in a more usefull format
            String[] coords = coord.split(",");
            Integer column = Integer.parseInt(coords[0]);
            Integer row = Integer.parseInt(coords[1]);

            // Create new basin to start logging numbers
            basinSizes.add(explore(checkedCoords, column, row));
        }
        Collections.sort(basinSizes, Collections.reverseOrder());
        System.out.println("Basin sizes:");
        System.out.println(basinSizes);
        // Get top three and multiply
        return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    private Integer explore(List<String> checkedCoords, Integer column, Integer row) {
        var center = caveTopo.getCoords(column,row);
        // A peak or null then ignore
        if (center == null || center.equals(9)) {
            return 0;
        }
        String key = column + "," + (row-1);
        // Next get the coord key for the point, if it has already been checked & logged then skip
        if (checkedCoords.contains(key)) {
            return 0;
        }
        // Else log this point, set size & check neighbours recursively
        checkedCoords.add(key);
        Integer size = 1;
        size += explore(checkedCoords, column, row-1);
        size += explore(checkedCoords, column+1, row);
        size += explore(checkedCoords, column, row+1);
        size += explore(checkedCoords, column-1, row);
        return size;
    }


}



