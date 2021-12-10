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
    public static List<Integer> stringOfIntegersToList(String str) {
        return Arrays.asList(str.split("")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public static Integer sumIntegerList(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public static Double stdDevIntegerList(List<Integer> list) {
        Integer sum = sumIntegerList(list);
        Double mean = Double.valueOf(sum) / list.size();

        Double totalDeviation = Double.valueOf(0);
        for (Integer value: list) {
            totalDeviation += Math.pow((value.doubleValue() - mean), 2);
        }

        return Math.sqrt(totalDeviation / list.size());
    }

    public static Integer calcSumOfTriangleNumberBecauseIDontKnowWhatItsCalled(Integer num) {
        Integer sum = 0;
        for(Integer i = 0; i <= num; i++) {
            sum += i;
        }
        return sum;
    }

    public static String sortStringChars(String str)
    {
        char chars[] = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static String integerListToString(List<Integer> list) {
        return list.stream().map(String::valueOf)
                .collect(Collectors.joining(""));
    }
}
