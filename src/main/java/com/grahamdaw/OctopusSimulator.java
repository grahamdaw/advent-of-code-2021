package com.grahamdaw;

import com.grahamdaw.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

import static com.grahamdaw.Utils.stringOfIntegersToList;

public class OctopusSimulator {
    private Matrix octopuses = new Matrix();

    public void parseAndAddRow(String csv){
        octopuses.addRow(stringOfIntegersToList(csv));
    }

    public Integer flashesInSteps(Integer steps) {
        Integer totalFlashes = 0;
        // System.out.println("--- Starting ---");
        System.out.println("\n");
        for(int i = 1; i <= steps; i++) {
            // System.out.println("--- Step " + i + "---");
            // 1. add 1 to all & get an initial flash list
            var willFlashFirst = chargeStep();
            // 2. Iterate this recursively to trigger flashes
            for(List<Integer> octopusCoords: willFlashFirst) {
                flashOctopus(octopusCoords.get(0), octopusCoords.get(1));
            }
            // 3. Count all flashing octopuses & reset them to 0
            Integer flashes = countFlashesAndReset();
            totalFlashes += flashes;
            System.out.println("Step " + i + " total flashes " + totalFlashes + " (+"+flashes+")");
            // System.out.println("\nEnd state:");
            // octopuses.print();
            // System.out.println("\n");
        }
        return totalFlashes;
    }

    public Integer findFirstSynchornizedFlash() {
        // Todo make sure this starts with unmutated input
        Integer totalSquid = 100; // TODO this can be put somewhere better
        Integer step = 1;
        while(true) {
            // 1. add 1 to all & get an initial flash list
            var willFlashFirst = chargeStep();
            // 2. Iterate this recursively to trigger flashes
            for(List<Integer> octopusCoords: willFlashFirst) {
                flashOctopus(octopusCoords.get(0), octopusCoords.get(1));
            }
            // 3. Count all flashing octopuses & reset them to 0
            Integer stepFlashes = countFlashesAndReset();
            System.out.println("Step " + step + " total flashes this step : " + stepFlashes);
            if (stepFlashes.equals(totalSquid)) {
                break;
            }
            step++;
        }
        return step;
    }

    private List<List<Integer>> chargeStep() {
        List<List<Integer>> willFlash = new ArrayList<>();
        var rows = octopuses.getMatrix();
        for (int y = 0; y < rows.size(); y ++) {
            var row = rows.get(y);
            for (int x = 0; x < row.size(); x ++) {
                Integer newValue = row.get(x) + 1;
                if (newValue.intValue() == 10) {
                    willFlash.add(new ArrayList<>(List.of(x, y)));
                }
                // Tidy this us
                if (newValue <= 10) {
                    row.set(x, newValue);
                }
            }
        }
        return willFlash;
    }

    private void flashOctopus(Integer x, Integer y) {
        var surrounding = octopuses.getSurroundingCoords(x.intValue(),y.intValue());
        for(List<Integer> octo: surrounding) {
            // System.out.println("> > Checking surrounding " + x + "," + y);
            Integer oldValue = octopuses.getCoords(octo.get(0), octo.get(1));
            Integer newValue = oldValue + 1;
            if (newValue.intValue() >= 10) {
                octopuses.setCoords(octo.get(0), octo.get(1), 10);
            } else {
                octopuses.setCoords(octo.get(0), octo.get(1), newValue);
            }
            if (newValue.intValue() == 10) {
                // Only recurse if we haven't recursed already
                flashOctopus(octo.get(0), octo.get(1));
            }
        }
    }

    private Integer countFlashesAndReset() {
        Integer totalFlashes = 0;
        var rows = octopuses.getMatrix();
        for (int y = 0; y < rows.size(); y++) {
            var row = rows.get(y);
            for (int x = 0; x < row.size(); x++) {
                var value = octopuses.getCoords(x, y);
                if (value > 9) {
                    octopuses.setCoords(x, y, 0);
                    totalFlashes += 1;
                }
            }
        }
        return totalFlashes;
    }
}
