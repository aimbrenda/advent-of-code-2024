package day2;

import day1.Day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        var filePath = "/day2.txt";

        var reports = new LinkedList<List<Integer>>();

        readLists(filePath, reports);

        int safeReports = 0 ;
        for ( List<Integer> levels : reports) {
            int direction = 0;
            for ( int i = 1; i < levels.size(); i++) {
                // define direction
                if( i == 1 ) {
                    direction = (levels.get(0) >= levels.get(1)) ? -1 : 1;
                }

                int distance = levels.get(i) - levels.get(i-1);

                if(Integer.signum(distance) != direction || Math.abs(distance) >3 || Math.abs(distance) < 1) {
                    break;
                } else if(i == levels.size()-1) {
                    safeReports++;
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
                list.add(Arrays.stream(numbers).map(Integer::parseInt).toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
