package com.grahamdaw;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    // The way we are selecting the solution for the day until I have time to make a better way
    static Integer day = 1;
    // Let's just consider the file is here
    static String file = "data/depth-readings.txt";

    public static void main(String[] args) {
        try{
            switch(day){
                case 1:
                    System.out.println("~~~ Day 1 solution ~~~");
                    DepthMeasurements dm = new DepthMeasurements();
                    getDepthReadings().forEach((line) -> {
                        dm.addMeasurement(Integer.parseInt(line));
                    });
                    System.out.println("We have gone " + dm.getTimesDeeper() + " times deeper");
                    break;
                default:
                    System.out.println("No solution for day " + day + " yet!");
            }
        } catch (Exception e) {
            System.err.println("Well that didn't work...");
            e.printStackTrace();
        }
    }

    private static Stream<String> getDepthReadings() throws IOException {
        return Files.lines(Paths.get(file));
    }
}
