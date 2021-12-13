package com.grahamdaw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.grahamdaw.Utils.*;

public class Main {

    // The way we are selecting the solution for the day until I have time to make a better way
    static Integer day = 12;
    // Let's just consider the file is here in case we need to reused them anywhere
    static String basePath = "src/main/resources/";
    static String depthReadings = basePath + "depth-readings.txt";
    static String navReadings = basePath + "nav-data.txt";
    static String diagReadings = basePath + "diagnostics.txt";
    static String bingoStream = basePath + "bingo-stream.txt";
    static String bingoCards = basePath + "bigo-cards.txt";
    static String topology = basePath + "topo.txt";
    static String lanternFish = basePath + "lantern-fish.txt";
    static String crabSubs = basePath + "crab-subs.txt";
    static String numberDisplay = basePath + "number-display.txt";
    static String heightMap = basePath + "heightmap.txt";
    static String navErrors = basePath + "nav-system-errors.txt";
    static String octopuses = basePath + "dumbo-octo.txt";
    static String caveSystem = basePath + "cave-system.txt";

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
                case 5 -> {
                    System.out.println("~~~ Day 5 solution ~~~");
                    Topology topo = new Topology();
                    getFileStream(topology).forEach((line) -> {
                        String[] coords = line.split("\\s+->\\s+");
                        topo.overlayLine(Topology.stringToCoords(coords[0]), Topology.stringToCoords(coords[1]));
                    });
                    System.out.println("Number of peaks " + topo.getPeaks());
                }
                case 6 -> {
                    System.out.println("~~~ Day 6 solution ~~~");
                    int daysToRun = 256;
                    List<Integer> startingFish = csvStringToList(getFileAsString(lanternFish));
                    FishBreedingProgramV2 fbp = new FishBreedingProgramV2(startingFish);

                    while(fbp.getElapsedDays() < daysToRun) {
                        fbp.nextDay();
                        fbp.printStatus();
                    }
                }
                case 7 -> {
                    System.out.println("~~~ Day 7 solution ~~~");
                    List<Integer> crabSubPositions = csvStringToList(getFileAsString(crabSubs));
                    CrabSubOptimizer cso = new CrabSubOptimizer(crabSubPositions);
                    System.out.println("Answer part 1: " + cso.moveBruteForcePart1());
                    System.out.println("Answer part 2: " + cso.moveBruteForcePart2());
                }
                case 8 -> {
                    System.out.println("~~~ Day 8 solution ~~~");
                    List<Integer> rows = new ArrayList<>();
                    getFileStream(numberDisplay).forEach((line) -> {
                        String[] parsed = Pattern.compile("\\s+\\|\\s+").split(line);
                        rows.add(NumberDecoder.decodeNumbers(parsed[0], parsed[1]));
                    });
                    // Answer part 1 removed as part 2 it changed the return type of decodeNumbers
                    Integer total = 0;
                    for(Integer num: rows) {
                        total += num;
                    }
                    System.out.println("Answer part 2: " + total);
                }
                case 9 -> {
                    System.out.println("~~~ Day 9 solution ~~~");
                    CaveMapper cm = new CaveMapper();
                    getFileStream(heightMap).forEach((line) -> cm.parseAndAddRow(line));
                    cm.printMap();
                    System.out.println("Answer part 1: " + cm.calculateTotalRiskLevel());
                    System.out.println("Answer part 2: " + cm.findBasins());
                }
                case 10 -> {
                    System.out.println("~~~ Day 10 solution ~~~");
                    NavSubsysParser nav = new NavSubsysParser();
                    getFileStream(navErrors).forEach((line) -> nav.addDataRow(line));

                    System.out.println("Answer part 1: " + nav.syntaxErrorScore());
                    System.out.println("Answer part 2: " + nav.autocompleteScore());
                }
                case 11 -> {
                    System.out.println("~~~ Day 11 solution ~~~");
                    OctopusSimulator sim = new OctopusSimulator();
                    getFileStream(octopuses).forEach((line) -> sim.parseAndAddRow(line));
                    System.out.println("Answer part 1: " + sim.flashesInSteps(100));
                    OctopusSimulator sim2 = new OctopusSimulator();
                    getFileStream(octopuses).forEach((line) -> sim2.parseAndAddRow(line));
                    System.out.println("Answer part 2: " + sim2.findFirstSynchornizedFlash());
                }
                case 12 -> {
                    System.out.println("~~~ Day 12 solution ~~~");
                    CaveNavigator nav = new CaveNavigator();
                    getFileStream(caveSystem).forEach((line) -> {
                        String[] conn = line.split("-");
                        nav.addCaveConnection(conn[0], conn[1]);
                    });
                    System.out.println("Answer: " + nav.calculateRoutes());
                }
                default -> System.out.println("No solution for day " + day + " yet!");
            }
        } catch (Exception e) {
            System.err.println("Well that didn't work...");
            e.printStackTrace();
        }
    }
}
