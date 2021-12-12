package com.grahamdaw.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {

        private List<List<Integer>> matrix = new ArrayList<>();

        public void addRow(List<Integer> list) {
            matrix.add(list);
        }

        public List<List<Integer>> getMatrix() {
            return matrix;
        }

        public Integer getCoords(int x, int y) {
            try {
                return matrix.get(y).get(x);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }

        public void setCoords(int x, int y, Integer value) {
            try {
                matrix.get(y).set(x, value);
            } catch (IndexOutOfBoundsException e) {
            }
        }

        public List<Integer> getAdjacentCoordsValues(int x, int y) {
            // Clockwise from top
            return new ArrayList<>(List.of(
                    getCoords(x, y-1),
                    getCoords(x+1, y),
                    getCoords(x, y+1),
                    getCoords(x-1, y)
            ));
        }

        public List<Integer> getSurroundingCoordsValues(int x, int y) {
            // Clockwise from top
            return new ArrayList<>(List.of(
                    getCoords(x, y-1),
                    getCoords(x+1, y-1),
                    getCoords(x+1, y),
                    getCoords(x+1, y+1),
                    getCoords(x, y+1),
                    getCoords(x-1, y+1),
                    getCoords(x-1, y),
                    getCoords(x-1, y-1)
            ));
        }

    public List<List<Integer>> getSurroundingCoords(int x, int y) {
        // Clockwise from top
        List<List<Integer>> coords = new ArrayList<>();
        if (getCoords(x, y-1) != null) {
            coords.add(new ArrayList<>(List.of(x, y-1)));
        }
        if (getCoords(x+1, y-1) != null) {
            coords.add(new ArrayList<>(List.of(x+1, y-1)));
        }
        if (getCoords(x+1, y) != null) {
            coords.add(new ArrayList<>(List.of(x+1, y)));
        }
        if (getCoords(x+1, y+1) != null) {
            coords.add(new ArrayList<>(List.of(x+1, y+1)));
        }
        if (getCoords(x, y+1) != null) {
            coords.add(new ArrayList<>(List.of(x, y+1)));
        }
        if (getCoords(x-1, y) != null) {
            coords.add(new ArrayList<>(List.of(x-1, y)));
        }
        if (getCoords(x-1, y+1) != null) {
            coords.add(new ArrayList<>(List.of(x-1, y+1)));
        }
        if (getCoords(x-1, y-1) != null) {
            coords.add(new ArrayList<>(List.of(x-1, y-1)));
        }
        return coords;
    }

        public void print() {
            for (List<Integer> row: matrix) {
                System.out.println(row.stream().map((i) -> Integer.toString(i)).collect(Collectors.joining(" ")));
            }
        }
    }

