package com.grahamdaw;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // The way we are selecting the solution for the day until I have time to make a better way
    static Integer day = 4;
    // Let's just consider the file is here
    static String depthReadings = "data/depth-readings.txt";
    static String navReadings = "data/nav-data.txt";
    static String diagReadings = "data/diagnostics.txt";
    static String bingoStream = "data/bingo-stream.txt";
    static String bingoCards = "data/bingo-cards.txt";


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
                case 4 -> {
                    System.out.println("~~~ Day 4 solution ~~~");
                    List<String> rows = new ArrayList<String>();
                    Bingo bingo = new Bingo();
                    // Load cards
                    getFileStream(bingoCards).forEach((line) -> {
                        if (line.length() == 0) {
                            bingo.saveCurrentCardAndReset();
                        } else {
                            bingo.addRowToCurrentCard(line);
                        }
                    });
                    // Hack to save last card
                    // TODO check out better way after finding solution
                    bingo.saveCurrentCardAndReset();
                    // Load number stream
                    List<Integer> bingoNumbers = Arrays.asList(Files.readString(Path.of(bingoStream)).split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
                    // Now bring the bingo!
                    Integer currentNumber = null;

                    // Part 1 solution
                    /* for (int i = 0; i < bingoNumbers.size(); i++) {
                        currentNumber = bingoNumbers.get(i);
                        bingo.drawBingoNumberPart1(currentNumber);
                        System.out.println("Drawing number " + currentNumber);
                        if (bingo.isWinner()) {
                            System.out.println("We have a BINGO!");
                            break;
                        }
                    }
                    // Get winner & score
                    System.out.println("The winning card scored " + bingo.winningScore(currentNumber)); */

                    // Part 2 solution
                    for (int i = 0; i < bingoNumbers.size(); i++) {
                        currentNumber = bingoNumbers.get(i);
                        bingo.drawBingoNumberPart2(currentNumber);
                        System.out.println("Drawing number " + currentNumber);
                        if (!bingo.isRemainingCards()) {
                            System.out.println("We finally have the last BINGO!");
                            break;
                        }
                    }
                    // Get winner & score
                    System.out.println("The last card scored " + bingo.lastCardScore(currentNumber));
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
