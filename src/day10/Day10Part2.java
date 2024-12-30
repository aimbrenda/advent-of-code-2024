package day10;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10Part2 {

    public static void main(String[] args) {
        var filePath = "/day10.txt";
        List<List<Integer>> map = readLists(filePath);

        int totalScore = 0;
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) == 0) {
                    totalScore += walkTrail(x, y, map);
                }
            }
        }

        System.out.println("Total Score: " + totalScore);

    }

    private static List<List<Integer>> readLists(String filePath) {

        List<List<Integer>> map = new ArrayList<>();

        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                map.add(new ArrayList<>());
                List<String> values = line.trim().chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList());

                for(int j=0; j< values.size(); j++){
                    map.get(map.size()-1).add(Integer.valueOf(values.get(j)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private static int walkTrail(int x, int y,List<List<Integer>> map) {
        int height = map.get(y).get(x);
        if (height == 9) {
            return 1;
        }

        int score = 0;
        // left
        if (x > 0 && map.get(y).get(x-1) == height + 1) {
            score += walkTrail(x - 1, y, map);
        }
        // up
        if (y > 0 && map.get(y-1).get(x) == height + 1) {
            score += walkTrail(x, y - 1, map);
        }
        // right
        if (x < map.get(y).size() - 1 && map.get(y).get(x+1) == height + 1) {
            score += walkTrail(x + 1, y, map);
        }
        // down
        if (y < map.size() - 1 && map.get(y+1).get(x) == height + 1) {
            score += walkTrail(x, y + 1, map);
        }

        return score;
    }

    private static class Point {
        final int x;
        final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(x);
            result = 31 * result + Integer.hashCode(y);
            return result;
        }
    }
}
