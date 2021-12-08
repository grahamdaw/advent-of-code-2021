package com.grahamdaw;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FishBreedingProgramV2 {
    // New approach as v1 breaks with large number of fish
    // Keep track of batches of fish by day, rather than the individual fish
    private List<BigInteger> fish = new ArrayList<>(Collections.nCopies(9, new BigInteger("0")));
    private int elapsedDays = 0;

    public FishBreedingProgramV2(List<Integer> startingFish) {
        for (Integer age: startingFish) {
            fish.set(age, fish.get(age).add(new BigInteger("1")));
        }
    }

    public void nextDay() {
        List<BigInteger> newFish = new ArrayList<BigInteger>(Collections.nCopies(9, new BigInteger("0")));
        for (int i = 0; i < fish.size(); i++) {
            if (i == 0) {
                newFish.set(6, newFish.get(6).add(fish.get(0)));
                newFish.set(8, newFish.get(8).add(fish.get(0)));
            } else {
                newFish.set(i-1, newFish.get(i-1).add(fish.get(i)));
            }

        }
        fish = newFish;
        elapsedDays++;
    }

    public Integer getElapsedDays() {
        return elapsedDays;
    }

    public void printStatus() {
        BigInteger total = new BigInteger("0");
        for(int i = 0; i < fish.size(); i++){
            total = total.add(fish.get(i));
        }
        System.out.println("Day " +  elapsedDays + " : " + total + " fish");
    }
}
