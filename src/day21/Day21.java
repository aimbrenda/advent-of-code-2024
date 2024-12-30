package day21;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day21 {


    public static void main(String[] args) {
        var filePath = "/day21.txt";

        List<String> lines = readLists(filePath);
        Day21 day21 = new Day21();
        day21.findNumPadMoves();
        day21.findDirPadMoves();

        var part1Answer = day21.getTotalComplexity(lines, 25);
        System.out.println(part1Answer);

    }

    private final Map<Character, Map<Integer, Integer>> numPadCells = Map.ofEntries(
            Map.entry('7', Map.of(0, 0)),
            Map.entry('8', Map.of(0, 1)),
            Map.entry('9', Map.of(0, 2)),
            Map.entry('4', Map.of(1, 0)),
            Map.entry('5', Map.of(1, 1)),
            Map.entry('6', Map.of(1, 2)),
            Map.entry('1', Map.of(2, 0)),
            Map.entry('2', Map.of(2, 1)),
            Map.entry('3', Map.of(2, 2)),
            Map.entry('0', Map.of(3, 1)),
            Map.entry('A', Map.of(3, 2))
    );

    private final Map<Character, Map<Integer, Integer>> dirPadCells = Map.of(
            '^', Map.of(0, 1),
            'A', Map.of(0, 2),
            '<', Map.of(1, 0),
            'v', Map.of(1, 1),
            '>', Map.of(1, 2)
    );

    private final Map<Map<Character, Character>, String> numPadMoves = new HashMap<>();
    private final Map<Map<Character, Character>, String> dirPadMoves = new HashMap<>();

    private final Map<Map<Integer, String>, Long> moveCache = new HashMap<>();




    private void findNumPadMoves() {
        for (char from : numPadCells.keySet()) {
            for (char to : numPadCells.keySet()) {
                numPadMoves.put(Map.of(from, to), generateNumPadMoves(numPadCells.get(from), numPadCells.get(to)));
            }
        }
    }

    private void findDirPadMoves() {
        for (char from : dirPadCells.keySet()) {
            for (char to : dirPadCells.keySet()) {
                dirPadMoves.put(Map.of(from, to), generateDirPadMoves(dirPadCells.get(from), dirPadCells.get(to)));
            }
        }
    }

    private long getTotalComplexity(List<String> lines, int dirPadCount) {
        return lines.stream().map(line -> getLineComplexity(line, dirPadCount)).reduce(Long::sum).orElse(-1L);
    }

    private long getLineComplexity(String line, int dirPadCount) {
        return Long.parseLong(line.substring(0, 3), 10) * countSteps(getNumpadMoves(line), dirPadCount);
    }

    private String getNumpadMoves(String line) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            output.append(numPadMoves.get(Map.of((i == 0 ? 'A' : line.charAt(i - 1)), line.charAt(i))));
        }
        return output.toString();
    }

    private long countSteps(String line, int dirPadCount) {
        if (dirPadCount == 0) return line.length();
        if ("A".equals(line)) return 1;
        Map<Integer, String> key = Map.of(dirPadCount, line);
        if (moveCache.containsKey(key)) return moveCache.get(key);
        long sum = 0;
        String[] moves = line.split("A");
        for (String move : moves) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i <= move.length(); i++) {
                output.append(dirPadMoves.get(
                        Map.of(
                                (i == 0 ? 'A' : move.charAt(i - 1)),
                                (i == move.length() ? 'A' : move.charAt(i)))
                ));
            }
            sum += countSteps(output.toString(), dirPadCount - 1);
        }
        moveCache.put(key, sum);
        return sum;
    }



    private String generateNumPadMoves(Map<Integer, Integer> startPos, Map<Integer, Integer> endPos) {
        boolean vertFirst = getValueFromMap(startPos) <= getValueFromMap(endPos);
        if (getKeyFromMap(startPos) == 3 && getValueFromMap(endPos) == 0) vertFirst = true;
        if (getValueFromMap(startPos) == 0 && getKeyFromMap(endPos) == 3) vertFirst = false;
        return generateMoves(startPos, endPos, vertFirst);
    }

    private String generateDirPadMoves(Map<Integer, Integer> startPos, Map<Integer, Integer> endPos) {
        boolean vertFirst = getValueFromMap(startPos) <= getValueFromMap(endPos);
        if (getKeyFromMap(startPos) == 0 && getValueFromMap(endPos) == 0) vertFirst = true;
        if (getValueFromMap(startPos) == 0 && getKeyFromMap(endPos) == 0) vertFirst = false;
        return generateMoves(startPos, endPos, vertFirst);
    }

    private String generateMoves(Map<Integer, Integer> startPos, Map<Integer, Integer> endPos, boolean vertFirst) {
        StringBuilder result = new StringBuilder();
        if (vertFirst) {
            int vertDiff = getKeyFromMap(endPos) - getKeyFromMap(startPos);
            while (vertDiff < 0) {
                result.append("^");
                vertDiff++;
            }
            while (vertDiff > 0) {
                result.append("v");
                vertDiff--;
            }
        }
        int horzDiff = getValueFromMap(endPos) - getValueFromMap(startPos);
        while (horzDiff < 0) {
            result.append("<");
            horzDiff++;
        }
        while (horzDiff > 0) {
            result.append(">");
            horzDiff--;
        }
        if (!vertFirst) {
            int vertDiff = getKeyFromMap(endPos) - getKeyFromMap(startPos);
            while (vertDiff < 0) {
                result.append("^");
                vertDiff++;
            }
            while (vertDiff > 0) {
                result.append("v");
                vertDiff--;
            }
        }
        return result + "A";
    }






    <T> T getValueFromMap(Map<?, T> map){
        List<T> values = new ArrayList<>(map.values());
        return values.get(0);
    }

    <T> T getKeyFromMap(Map<T, ?> map){
        List<T> values = new ArrayList<>(map.keySet());
        return values.get(0);
    }





    private static List<String> readLists(String filePath) {
        List<String> strings = new ArrayList<>();
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return strings;
    }


}
