package com.grahamdaw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static Stream<String> getFileStream(String file) throws IOException {
        return Files.lines(Paths.get(file));
    }

    public static String getFileAsString(String file) throws IOException {
        return Files.readString(Paths.get(file));
    }

    public static List<Integer> csvStringToList(String csv) {
        return Arrays.asList(csv.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
