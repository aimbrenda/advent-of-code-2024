package day2;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day2Part2 {

    public static void main(String[] args) {
        var filePath = "/day2.txt";

        var reports = new LinkedList<List<Integer>>();

        readLists(filePath, reports);

        int safeReports = 0 ;
        for ( List<Integer> levels : reports) {
            int direction = 0;

            for(int j = 0; j <= levels.size(); j++) {
                int unsafeLevels = 0;
                var l = new ArrayList<>(levels);
                if(j > 0) {
                    l.remove(j-1);
                }
                for (int i = 1; i < l.size(); i++) {
                    // define direction
                    if (i == 1) {
                        direction = (l.get(0) >= l.get(1)) ? -1 : 1;
                    }

                    int distance = l.get(i) - l.get(i - 1);

                    if (Integer.signum(distance) != direction || Math.abs(distance) > 3 || Math.abs(distance) < 1) {
                        unsafeLevels++;
                    } else if (i == l.size() - 1 && unsafeLevels == 0) {
                        safeReports++;
                    }

                }

                if(unsafeLevels == 0) {
                    break;
                }
            }
        }

        System.out.println(safeReports);
    }

    private static void readLists(String filePath, List<List<Integer>> list) {
        try (InputStream inputStream = Day1.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                var numbers = line.trim().split("\\s+");
                list.add(new ArrayList<>(Arrays.stream(numbers).map(Integer::parseInt).toList()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
