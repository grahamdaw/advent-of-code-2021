package com.grahamdaw;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    // The way we are selecting the solution for the day until I have time to make a better way
    static Integer day = 3;
    // Let's just consider the file is here
    static String depthReadings = "data/depth-readings.txt";
    static String navReadings = "data/nav-data.txt";
    static String diagReadings = "data/diagnostics.txt";


    public static void main(String[] args) {
        try{
            switch (day) {
                case 1 -> {
                    System.out.println("~~~ Day 1 solution ~~~");
                    Depth dm = new Depth();
                    getFileStream(depthReadings).forEach((line) -> dm.addMeasurement(Integer.parseInt(line)));
                    System.out.println("We have gone " + dm.getTimesDeeper() + " times deeper");
                }
                case 2 -> {
                    System.out.println("~~~ Day 2 solution ~~~");
                    Navigation nav = new Navigation();
                    getFileStream(navReadings).forEach(nav::addMovementCommand);
                    System.out.println("Our location (for the purposes of the challenge) is " + nav.getLocation());
                }
                case 3 -> {
                    System.out.println("~~~ Day 3 solution ~~~");
                    Diagnostics diagnostics = new Diagnostics();
                    getFileStream(diagReadings).forEach(diagnostics::addReading);
                    System.out.println("Power consumption is " + diagnostics.calculatePowerConsumption());
                    System.out.println("Life support rating is " + diagnostics.calculateLifeSupport());
                }
                default -> System.out.println("No solution for day " + day + " yet!");
            }
        } catch (Exception e) {
            System.err.println("Well that didn't work...");
            e.printStackTrace();
        }
    }

    private static Stream<String> getFileStream(String file) throws IOException {
        return Files.lines(Paths.get(file));
    }
}
