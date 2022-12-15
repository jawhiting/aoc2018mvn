package com.drinkscabinet.aoc2022;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = Day1.class.getResource("/2022/day1.txt");
        assert resource != null;
        List<String> lines = Files.readAllLines(Path.of(resource.toURI()));

        new Day1().part1(lines);
        new Day1().part2(lines);
    }

    private void part1(List<String> lines) {
        System.out.println(parse(lines).stream().max(Integer::compareTo).orElseThrow());
    }

    private void part2(List<String> lines) {
        System.out.println(parse(lines).stream().sorted(Collections.reverseOrder()).mapToInt(Integer::intValue).limit(3).sum());
    }

    @NotNull
    private static List<Integer> parse(List<String> lines) {
        List<Integer> result = new LinkedList<>();
        int val = 0;
        for (String line: lines) {
            if( !line.isBlank()) {
                val += Integer.parseInt(line);
            }
            else {
                result.add(val);
                val = 0;
            }
        }
        return result;
    }
}
