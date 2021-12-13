package com.grahamdaw;

import java.util.*;
import java.util.stream.Collectors;

public class CaveNavigator {
    Map<String, List<String>> connections = new HashMap<>();
    final String startCave = "start";
    final String endCave = "end";
    Map<String, List<String>> routesPart1 = new HashMap<>();
    Map<String, List<String>> routesPart2 = new HashMap<>();

    public void addCaveConnection(String a, String b){
        // Add connection of a to b
        if (connections.get(a) == null){
            connections.put(a, new ArrayList<>());
        }
        if (connections.get(a).indexOf(b) < 0) {
            connections.get(a).add(b);
        }

        // Add connection of b to a
        if (connections.get(b) == null){
            connections.put(b, new ArrayList<>());
        }
        if (connections.get(b).indexOf(a) < 0) {
            connections.get(b).add(a);
        }
    }

    public String calculateRoutes() {
        printConnections();
        System.out.println("\nPlanning routes...");

        for(String connected: connections.get(startCave)) {
            exploreRoutePart1(new ArrayList<>(), startCave, connected);
        }
        printRoutesPart1();
        // Rules changed for part 2
        for(String connected: connections.get(startCave)) {
            exploreRoutePart2(new ArrayList<>(), startCave, connected);
        }
        printRoutesPart2();
        return "Part 1: " + routesPart1.size() + " | Part 2: " + routesPart2.size();
    }

    private void exploreRoutePart1(List<String> path, String current, String next) {
        if (current.equalsIgnoreCase(endCave)) {
            // We have finished - add the end cave & store the result
            path.add(current);
            routesPart1.put(listToPath(path), path);
            return;
        }
        // Else we must go deeper! However, there are rules about what we can do next
        // (These conditionals are deliberately kept verbose here for debugging)
        if (next.equalsIgnoreCase(startCave)) {
            // We can't go backwards to the start cave.
            return;
        }
        if (isSmallCave(current) && connections.get(next).size() <= 1) {
            // We cannot go straight back the way we came unless the next cave is a dead end
            // & our current cave is a large cave (we can't visit small caves more than once)
            return;
        }
        if (next.equals(current)) {
            // This shouldn't happen in the data, but we
            // definitely shouldn't do this.
            return;
        }
        if (getSmallCaves(path).indexOf(next) >= 0) {
            // Finally, we can only visit small caves once - split the path & check
            // if the current cave matches any of the visited small caves.
            return;
        }

        path.add(current);
        for(String connected: connections.get(next)) {
            // Make sure to clone the path list to avoid weird results
            exploreRoutePart1(new ArrayList<>(path), next, connected);
        }
    }

    private void exploreRoutePart2(List<String> path, String current, String next) {
        if (current.equalsIgnoreCase(endCave)) {
            // We have finished - add the end cave & store the result
            path.add(current);
            routesPart2.put(listToPath(path), path);
            return;
        }
        // Else we must go deeper! However, there are rules about what we can do next
        // (These conditionals are deliberately kept verbose here for debugging)
        if (next.equalsIgnoreCase(startCave)) {
            // We can't go backwards to the start cave.
            return;
        }
        if (next.equals(current)) {
            // This shouldn't happen in the data, but we
            // definitely shouldn't do this.
            return;
        }
        if (visitedSameSmallCaveMoreThanOnce(path) && isSmallCave(current) && connections.get(next).size() <= 1) {
            // We cannot go straight back the way we came unless the next cave is a dead end
            // & our current cave is a large cave (we can't visit small caves more than once)
            // Also with part two we're allowed to visit a single small cave more than once.
            return;
        }
        if (visitedSameSmallCaveMoreThanOnce(path) && getSmallCaves(path).indexOf(current) >= 0) {
            // For part 2, we can visit A SINGLE small cave more than once, otherwise
            // the same rules apply as part 1.
            // if the current cave matches any of the visited small caves.
            return;
        }
        path.add(current);
        for(String connected: connections.get(next)) {
            // Make sure to clone the path list to avoid weird results
            exploreRoutePart2(new ArrayList<>(path), next, connected);
        }
    }


    private boolean isSmallCave(String cave){
        if (cave.toLowerCase().equals(cave) && !isStartCave(cave) && !isEndCave(cave)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLargeCave(String cave){
        if (cave.toUpperCase().equals(cave) && !isStartCave(cave) && !isEndCave(cave)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isStartCave(String cave){
        if (cave.equalsIgnoreCase(startCave)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEndCave(String cave){
        if (cave.equalsIgnoreCase(endCave)) {
            return true;
        } else {
            return false;
        }
    }

    private List<String> getSmallCaves(List<String> caves) {
        return caves.stream().filter(c -> isSmallCave(c)).collect(Collectors.toList());
    }

    private boolean visitedSameSmallCaveMoreThanOnce(List<String> caves) {
        List<String> smallCaves = getSmallCaves(caves);
        Set<String> uniqueCaves = new HashSet<>(smallCaves);

        if(uniqueCaves.size() == smallCaves.size()){
            return false;
        } else {
            return true;
        }
    }

    private List<String> getLargeCaves(List<String> caves) {
        return caves.stream().filter(c -> isLargeCave(c)).collect(Collectors.toList());
    }

    private void printConnections() {
        System.out.println("\n--- Cave Connections ---");
        for (Map.Entry<String, List<String>> entry : connections.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("\n");
    }

    private void printRoutesPart1() {
        System.out.println("\n--- Found " + routesPart1.size() + " Routes ---");
        for (Map.Entry<String, List<String>> entry : routesPart1.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println("\nIn case that was a very long output, there were " + routesPart1.size() + " routes.\n");
    }

    private void printRoutesPart2() {
        System.out.println("\n--- Found " + routesPart2.size() + " Routes ---");
        for (Map.Entry<String, List<String>> entry : routesPart2.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println("\nIn case that was a very long output, there were " + routesPart2.size() + " routes.\n");
    }

    private String listToPath(List<String> list){
        return list.stream().collect(Collectors.joining("-"));
    }

    private List<String> pathToList(String path){
        return Arrays.asList(path.split("-"));
    }

}
