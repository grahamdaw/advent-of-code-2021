package com.grahamdaw;

import java.util.ArrayList;
import java.util.List;

public class FishBreedingProgram {
    private List<Integer> fish = new ArrayList<Integer>();
    private int elapsedDays = 0;

    public FishBreedingProgram(List<Integer> startingFish) {
        fish = startingFish;
    }

    public void nextDay() {
        List<Integer> newFish = new ArrayList<Integer>();
        for (int i = 0; i < fish.size(); i++) {
            Integer age = fish.get(i);
            if (age.intValue() == 0) {
                // Congratulations, there is a new fish!
                fish.set(i, 6);
                newFish.add(8);
            } else {
                // The clock keeps ticking
                fish.set(i, age - 1);
            }
        }
        fish.addAll(newFish);
        elapsedDays++;
    }

    public Integer getElapsedDays() {
        return elapsedDays;
    }

    public void printStatus() {
        System.out.println("Day " +  elapsedDays + " : " + fish.size() + " fish");
    }
}
