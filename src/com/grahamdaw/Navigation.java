package com.grahamdaw;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class Navigation {

    private int aim = 0;

    private enum Coord {
        x, y
    }

    private enum Direction {
        up, down, forward
    }

    private final Map<Coord, Integer> location = new HashMap<>(Map.ofEntries(
            entry(Coord.x, 0),
            entry(Coord.y, 0)
    ));

    public void addMovementCommand(String command) {
        String[] cmd = command.split(" ", 2);
        if (cmd[0].equals(Direction.up.name())) {
            aim -= Integer.parseInt(cmd[1]);
        }
        if (cmd[0].equals(Direction.down.name())) {
            aim += Integer.parseInt(cmd[1]);
        }
        if (cmd[0].equals(Direction.forward.name())) {
            int dist = Integer.parseInt(cmd[1]);
            location.put(Coord.x, location.get(Coord.x) + dist);
            location.put(Coord.y, location.get(Coord.y) + (aim * dist));
        }
    }

    public Integer getLocation() {
        System.out.println("We have gone " + location);
        // Should have a better return type, but this works for the minute
        return location.get(Coord.x) * location.get(Coord.y);
    }
}
